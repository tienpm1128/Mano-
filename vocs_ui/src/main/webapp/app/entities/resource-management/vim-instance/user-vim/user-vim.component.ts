import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { IUserVim, NewUserVim, UserVim } from 'app/shared/model/userVim.model';
import { UserVimService } from 'app/entities/resource-management/vim-instance/user-vim/user-vim.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AccountService } from 'app/core';
import { ProjectService } from '../project/project.service';
import { RoleService } from '../role/role.service';
import { IProjects } from 'app/shared/model/projects.model';
import { IRole, RoleModel } from 'app/shared/model/roles.model';
import { ActivatedRoute } from '@angular/router';
import { Table } from 'primeng/table';

@Component({
  selector: 'jhi-user-vim',
  templateUrl: './user-vim.component.html',
  styleUrls: ['./user-vim.component.scss']
})
export class UserVimComponent implements OnInit {
  @ViewChild('select', { static: true }) select;
  @ViewChild('dt', { static: true }) dt: Table;

  selecteds = [];
  selectedProject: IProjects = null;
  selectedRole = new RoleModel();
  vimId: string;
  data: IUserVim[];
  projects: IProjects[];
  roles: IRole[];
  newUserVim = new NewUserVim();
  editUserVim = new NewUserVim();
  first = 0;
  lastRowsPerPage = 10;

  isDisplay = false;
  isEditing = false;
  isAdmin = false;
  isSaving = false;
  isCheckAll = false;

  confirmPassword = '';
  err = '';
  err_mess = '';

  blockSpace: RegExp = /[^\s]/;
  password_regex: RegExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#()'"\=\-\\\[\]~^|_/.,:;+])[A-Za-z\d@$!%*?&#()'"\=\-\\\[\]~^|_/.,:;+]{2,25}$/;

  constructor(
    private userVimService: UserVimService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private accountService: AccountService,
    private projectService: ProjectService,
    private roleService: RoleService,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
  }

  ngOnInit() {
    this.selectedRole.roleType = null;
    this.newUserVim.enabled = true;
    this.newUserVim.roles = [];
    this.newUserVim.projects = null;
    this.loadUserVim();
    this.loadProject();
    this.loadRole();
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
  }

  loadUserVim() {
    this.userVimService.get(this.vimId).subscribe(res => {
      if (res.body.errorCode === '00') {
        this.data = res.body.data;
        const selectedTemp = [];
        this.selecteds.forEach((selected: IUserVim) => {
          this.data.forEach(item => {
            if (selected.id === item.id) {
              selectedTemp.push(item);
            }
          });
        });
        this.selecteds = selectedTemp;
        this.check();
      } else {
        this.messageService.add({
          severity: 'error',
          summary: 'User Vim',
          detail: res.body.message,
          life: 10000
        });
      }
    });
  }

  loadProject() {
    this.projectService.query(this.vimId).subscribe(res => {
      if (res.body.errorCode === '00') {
        this.projects = res.body.data;
      }
    });
  }

  loadRole() {
    this.roleService.getRole(this.vimId).subscribe(res => {
      if (res.body.errorCode === '00') {
        this.roles = res.body.data;
      }
    });
  }

  confirmDelete(userVimID?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (userVimID) {
          this.userVimService.delete(userVimID, this.vimId).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({
                  severity: 'success',
                  summary: 'User Vim',
                  detail: 'Delete successfully!',
                  life: 10000
                });
                this.selecteds = this.selecteds.filter((item: IUserVim) => {
                  return item.id !== userVimID;
                });
                this.loadUserVim();
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'User Vim',
                  detail: res.body.message,
                  life: 10000
                });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'User Vim',
                detail: res.body.message,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          const length = this.selecteds.length;
          this.selecteds.forEach((user: IUserVim) => {
            this.userVimService.delete(user.id, this.vimId).subscribe(
              res => {
                if (res.body.errorCode === '00') {
                  count++;
                  if (count === length) {
                    this.messageService.add({
                      severity: 'success',
                      summary: 'User Vim',
                      detail: 'Delete successfully!',
                      life: 10000
                    });
                    this.selecteds = [];
                    this.loadUserVim();
                  }
                } else {
                  this.messageService.add({
                    severity: 'error',
                    summary: 'User Vim',
                    detail: res.body.message,
                    life: 10000
                  });
                }
                this.selecteds = [];
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'User Vim',
                  detail: res.body.message,
                  life: 10000
                });
              }
            );
          });
        }
      },
      reject: () => {}
    });
  }

  displayAddNew() {
    this.newUserVim = new NewUserVim();
    this.confirmPassword = null;
  }

  displayFormEdit(userVim: UserVim) {
    userVim.authorities = null;
    this.isEditing = true;
    this.isDisplay = false;
    this.editUserVim = Object.assign({}, userVim);
    this.selectedRole.roleType = userVim.roles[0].roleType;
    /*if (this.selectedRole.roleType !== null) {
      this.selectedRole.roleType = userVim.roles[0].roleType;
    }*/
  }

  reset() {
    this.err = '';
    this.err_mess = '';
    this.isEditing = false;
  }

  save() {
    if (this.selectedRole.roleType === 'null') {
      this.selectedRole.roleType = null;
    }

    this.isSaving = true;
    if (this.isEditing) {
      this.edit();
    } else {
      this.newUserVim.roles = [];

      if (this.selectedRole.roleType !== null) {
        this.selectedRole.authority = this.selectedRole.roleType;
        this.newUserVim.roles.push(this.selectedRole);
      }

      if (this.newUserVim.password === this.confirmPassword) {
        this.err_mess = '';
        this.userVimService.create(this.newUserVim, this.vimId).subscribe(
          res => {
            if (res.body.errorCode === '00') {
              this.loadUserVim();
              this.messageService.add({
                severity: 'success',
                summary: 'User Vim',
                detail: 'Create successfully!',
                life: 10000
              });
              this.isDisplay = false;
              this.isSaving = false;
            } else {
              this.messageService.add({
                severity: 'error',
                summary: 'User Vim',
                detail: res.body.message,
                life: 10000
              });
              this.isSaving = false;
            }
            this.isSaving = false;
          },
          res => {
            this.messageService.add({
              severity: 'error',
              summary: 'User Vim',
              detail: res.body.message,
              life: 10000
            });
            this.isSaving = false;
          }
        );
      } else {
        this.err = 'Confirm Password ';
        this.err_mess = 'Please enter the same password as above';
        this.isSaving = false;
      }
    }
  }

  edit() {
    this.editUserVim.roles = [];
    if (this.selectedRole.roleType !== null) {
      this.selectedRole.authority = this.selectedRole.roleType;
      this.editUserVim.roles.push(this.selectedRole);
    }

    this.userVimService.update(this.editUserVim, this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadUserVim();
          this.messageService.add({
            severity: 'success',
            summary: 'User Vim',
            detail: 'Update successfully!',
            life: 10000
          });
          this.isEditing = false;
          this.isSaving = false;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'User Vim',
            detail: res.body.message,
            life: 10000
          });
          this.isSaving = false;
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'User Vim',
          detail: res.message,
          life: 10000
        });
        this.isSaving = false;
      }
    );
  }

  replaceSpaceUsername() {
    this.newUserVim.username = this.newUserVim.username.replace(/\s/g, '');
  }

  replaceSpacePassword() {
    this.newUserVim.password = this.newUserVim.password.replace(/\s/g, '');
  }

  replaceSpaceConfirmPassword() {
    this.confirmPassword = this.confirmPassword.replace(/\s/g, '');
  }

  check() {
    const currentItem = this.dt.first + 1 + this.dt._rows > this.data.length ? this.data.length - this.dt.first : this.dt._rows;
    !this.dt.filteredValue || (this.dt.filteredValue && this.dt.filteredValue.length > 0)
      ? (this.isCheckAll = this.selecteds.length !== 0)
      : (this.isCheckAll = false);
    if (!this.dt.filteredValue) {
      for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
        if (!this.selecteds.includes(this.data[i])) {
          this.isCheckAll = false;
          break;
        }
      }
    } else {
      this.dt.filteredValue.forEach(item => {
        if (!this.selecteds.includes(item)) {
          this.isCheckAll = false;
        }
      });
    }
  }

  checkAll() {
    const currentItem = this.dt.first + 1 + this.dt._rows > this.dt.totalRecords ? this.dt.totalRecords - this.dt.first : this.dt._rows;
    if (!this.dt.filteredValue) {
      if (this.isCheckAll) {
        for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
          this.selecteds = [...this.selecteds, this.data[i]];
        }
      } else {
        for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
          this.selecteds = this.selecteds.filter(selected => {
            return this.data[i] !== selected;
          });
        }
      }
    } else {
      this.isCheckAll ? (this.selecteds = this.dt.filteredValue) : (this.selecteds = []);
    }
  }

  onSortOrPage() {
    if (this.dt.filteredValue && this.dt.filteredValue.length === 0) {
      this.isCheckAll = false;
    } else {
      const currentItem = this.dt.first + 1 + this.dt._rows > this.dt.totalRecords ? this.dt.totalRecords - this.dt.first : this.dt._rows;
      this.isCheckAll = true;
      for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
        if (!this.selecteds.includes(this.data[i])) {
          this.isCheckAll = false;
          break;
        }
      }
    }
    if (this.dt._rows !== this.lastRowsPerPage) {
      this.resetTable();
      this.lastRowsPerPage = this.dt._rows;
    }
  }

  resetTable() {
    this.first = -1;
    window.setTimeout(() => {
      this.first = 0;
      this.cdr.detectChanges();
    });
  }
}
