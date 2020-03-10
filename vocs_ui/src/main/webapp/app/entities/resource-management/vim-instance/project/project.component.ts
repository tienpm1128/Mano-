import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { ProjectService } from './project.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { IProjects, AddNewProjects, Projects } from 'app/shared/model/projects.model';
import { IUserVim } from 'app/shared/model/userVim.model';
import { UserVimService } from '../user-vim/user-vim.service';
import { AccountService } from 'app/core';
import { UserProject } from 'app/shared/model/userProject.model';
import { ActivatedRoute } from '@angular/router';
import { RoleService } from 'app/entities/resource-management/vim-instance/role/role.service';
import { IRoleVim } from 'app/shared/model/role.vim.model';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';

@Component({
  selector: 'jhi-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit {
  @ViewChild('projectForm', { static: true }) projectForm;
  @ViewChild('dt', { static: true }) dt;

  selecteds: IProjects[] = [];
  isCheckAll = false;
  data: IProjects[];
  userVims: IUserVim[];
  vimId: string;
  grantUserIds = [];
  newProject = new AddNewProjects();
  first = 0;
  lastRowsPerPage = 10;
  isDisplayCreateForm = false;
  isAdmin = false;
  isEditing = false;
  userProject = new UserProject();
  users: IUserVim[];
  selected: IUserVim[] = [];
  removing: IUserVim[] = [];
  selectedUsers: IUserVim[] = [];
  roles: IRoleVim[] = [];
  isSaving = false;

  constructor(
    private projectService: ProjectService,
    private userVimService: UserVimService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private accountService: AccountService,
    private vimUserService: UserVimService,
    private activatedRoute: ActivatedRoute,
    private roleService: RoleService,
    private cdr: ChangeDetectorRef
  ) {
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
  }

  ngOnInit() {
    this.loadData();
    this.loaduserVim();
    this.loadUsers();
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.roleService.query(this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.roles = res.body.data;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Role',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Role',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  loadData() {
    this.projectService.query(this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
          const selectedTemp = [];
          this.selecteds.forEach((selected: IProjects) => {
            this.data.forEach(item => {
              if (selected.projectId === item.projectId) {
                selectedTemp.push(item);
              }
            });
          });
          this.selecteds = selectedTemp;
          this.check();
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Project',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Project',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  loaduserVim() {
    this.userVimService.get(this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.userVims = res.body.data;
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Project',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  confirmDelete(projectId?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (projectId) {
          this.projectService.delete(projectId, this.vimId).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({
                  severity: 'success',
                  summary: 'Project',
                  detail: 'Delete successfully!',
                  life: 10000
                });
                this.selected = [];
                this.selecteds = this.selecteds.filter((item: IProjects) => {
                  return item.projectId !== projectId;
                });
                this.loadData();
              } else {
                this.messageService.add({ severity: 'error', summary: 'Project', detail: res.body.message, life: 10000 });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Project',
                detail: res.error.status + ' ' + res.error.message,
                life: 10000
              });
            }
          );
        } else if (this.selecteds) {
          let i = 0;
          this.selecteds.forEach(project => {
            this.projectService.delete(project.projectId, this.vimId).subscribe(
              res => {
                if (res.body.errorCode === '00') {
                  i++;
                  if (i === this.selecteds.length) {
                    this.selecteds = [];
                    this.loadData();
                    this.messageService.add({ severity: 'success', summary: 'Project', detail: 'Delete successfully!', life: 10000 });
                  }
                } else {
                  this.messageService.add({
                    severity: 'error',
                    summary: 'Project',
                    detail: 'Delete error',
                    life: 10000
                  });
                }
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Project',
                  detail: res.error.status + ' ' + res.error.message,
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

  save() {
    this.isSaving = true;
    if (this.isEditing) {
      this.newProject.userRoles = [];
      this.selectedUsers.forEach(user => {
        this.newProject.userRoles.push({
          roleId: user['role'],
          userId: user.id
        });
      });

      this.projectService.update(this.newProject, this.vimId).subscribe(
        res => {
          if (res.body.errorCode === '00') {
            this.isSaving = false;
            this.messageService.add({
              severity: 'success',
              summary: 'Project',
              detail: 'Update successfully!',
              life: 10000
            });
            this.isDisplayCreateForm = false;
            this.newProject = new Projects();
            this.selectedUsers = [];
            this.isEditing = false;
            this.isSaving = false;
            this.loadUsers();
            this.loadData();
          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'Project',
              detail: res.body.message,
              life: 10000
            });
            this.isSaving = false;
          }
        },
        res => {
          this.messageService.add({
            severity: 'error',
            summary: 'Project',
            detail: res.error.status + ' ' + res.error.message,
            life: 10000
          });
          this.isSaving = false;
        }
      );
    } else {
      this.newProject.enabled = false;
      this.projectService.create(this.newProject, this.vimId).subscribe(
        res => {
          if (res.body.errorCode === '00') {
            if (this.selectedUsers.length > 0) {
              let i = 0;
              this.userProject.grantProjectId = res.body.data.projectId;
              this.selectedUsers.forEach(user => {
                this.userProject.grantUserIds = [];
                this.userProject.grantUserIds.push(user.id);
                this.userProject.roleId = user['role'];
                this.projectService.grantUser(this.userProject, this.vimId).subscribe(response => {
                  if (response.body.errorCode === '00') {
                    i++;
                    if (i === this.selectedUsers.length) {
                      this.isSaving = false;
                      this.messageService.add({
                        severity: 'success',
                        summary: 'Project',
                        detail: 'Create successfully!',
                        life: 10000
                      });
                      this.newProject = new Projects();
                      this.selectedUsers = [];
                      this.loadUsers();
                      this.loadData();
                      this.isSaving = false;
                      this.isDisplayCreateForm = false;
                    }
                  } else {
                    this.isSaving = false;
                    this.messageService.add({ severity: 'error', summary: 'Project', detail: response.body.message, life: 10000 });
                  }
                });
              });
            } else {
              this.messageService.add({
                severity: 'success',
                summary: 'Project',
                detail: 'Create successfully!',
                life: 10000
              });
              this.newProject = new Projects();
              this.selectedUsers = [];
              this.loadUsers();
              this.loadData();
              this.isSaving = false;
              this.isDisplayCreateForm = false;
            }
          } else {
            this.messageService.add({ severity: 'error', summary: 'Project', detail: res.body.message, life: 10000 });
            this.isSaving = false;
          }
        },
        res => {
          this.messageService.add({
            severity: 'error',
            summary: 'Project',
            detail: res.error.status + ' ' + res.error.message,
            life: 10000
          });
          this.isSaving = false;
        }
      );
    }
    this.isSaving = false;
  }

  displayEditor(project?: IProjects, editing?: boolean) {
    this.isDisplayCreateForm = true;
    if (editing) {
      this.isEditing = true;
    }
    if (project) {
      this.newProject = { ...project };
      this.newProject.enabled = project.enabled;
      if (this.newProject.userRoles) {
        this.newProject.userRoles.forEach(userRole => {
          this.users.forEach(user => {
            if (user.id === userRole['userId']) {
              user['role'] = userRole['roleId'];
              this.selectedUsers.push(user);
              this.users.splice(this.users.indexOf(user), 1);
            }
          });
        });
      }
    }
  }

  closeEditor() {
    this.isDisplayCreateForm = false;
    this.isEditing = false;
    this.newProject = new Projects();
    this.loadUsers();
    this.selectedUsers = [];
  }

  resetForm() {
    Object.keys(this.newProject).forEach(index => {
      this.newProject[index] = null;
    });

    this.users = this.users.concat(this.selectedUsers);
    this.selectedUsers = [];
  }

  loadUsers() {
    this.vimUserService.get(this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.users = this.sortUsers(res.body.data);
        } else {
          this.messageService.add({ severity: 'error', summary: 'User Vim', detail: res.body.message, life: 10000 });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'User Vim',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  addUser() {
    if (this.selected === undefined || this.selected.length === 0) {
      return;
    }

    this.selected.forEach(user => {
      this.users.splice(this.users.indexOf(user), 1);
    });
    this.selectedUsers = this.selectedUsers.concat(this.selected);
    this.selected = [];
  }

  removeUser() {
    if (this.removing === undefined || this.removing.length === 0) {
      return;
    }
    this.removing.forEach(user => {
      let removeIndex = -1;
      if (this.newProject.userRoles) {
        this.newProject.userRoles.forEach((userRole, index) => {
          if (userRole.userId === user.id) {
            removeIndex = index;
          }
        });
      }
      if (removeIndex !== -1) {
        this.newProject.userRoles.splice(removeIndex, 1);
      }
      this.selectedUsers.splice(this.selectedUsers.indexOf(user), 1);
    });
    this.users = this.sortUsers(this.users.concat(this.removing));
    this.removing = [];
  }

  addAll() {
    this.selectedUsers = this.users.concat(this.selectedUsers);
    this.users = [];
  }

  removeAll() {
    this.users = this.sortUsers(this.selectedUsers.concat(this.users));
    this.selectedUsers = [];
  }

  sortUsers(userArr: IUserVim[]) {
    return userArr.sort((a, b) => {
      return a.username.localeCompare(b.username);
    });
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
