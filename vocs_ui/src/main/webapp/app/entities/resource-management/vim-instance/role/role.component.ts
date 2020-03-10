import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { RoleModel, IRoleVim, AddNewRoleModel } from 'app/shared/model/role.vim.model';
import { RoleService } from './role.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AccountService } from 'app/core';
import { ActivatedRoute } from '@angular/router';
import { INsSubscription } from 'app/shared/model/nsSubscription.model';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';

@Component({
  selector: 'jhi-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.scss']
})
export class RoleComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  selecteds = [];
  isCheckAll = false;
  newRole = new AddNewRoleModel();
  data: IRoleVim[];
  first = 0;
  lastRowsPerPage = 10;
  vimId: string;
  isDisplayCreate = false;
  isAdmin = false;
  isEditing = false;
  currentPageReportTemplate = '';
  err = '';
  err_mess = '';
  isSaving = false;

  constructor(
    private messageService: MessageService,
    private roleService: RoleService,
    private confirmationService: ConfirmationService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
  }

  ngOnInit() {
    this.loadData();
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
  }

  loadData() {
    this.roleService.getRole(this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
          const selectedTemp = [];
          this.selecteds.forEach((selected: IRoleVim) => {
            this.data.forEach(item => {
              if (selected.id === item.id) {
                selectedTemp.push(item);
              }
            });
          });
          this.selecteds = selectedTemp;
          this.check();
        } else {
          this.messageService.add({ severity: 'error', summary: 'Role', detail: res.body.message, life: 10000 });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Role',
          detail: res.body.error.status + ' ' + res.body.error.error,
          life: 10000
        });
      }
    );
  }

  confirmDelete(roleID?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (roleID) {
          this.roleService.delete(roleID, this.vimId).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'Role', detail: 'Delete successfully!', life: 10000 });
                this.selecteds = this.selecteds.filter((item: IRoleVim) => {
                  return item.id !== roleID;
                });
                this.loadData();
              } else {
                this.messageService.add({ severity: 'error', summary: 'Role', detail: res.body.message, life: 10000 });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Role',
                detail: res.body.error.status + ' ' + res.body.error.error,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          this.selecteds.forEach((role: IRoleVim) => {
            this.roleService.delete(role.id, this.vimId).subscribe(
              res => {
                if (res.body.errorCode === '00') {
                  count++;
                  if (count === this.selecteds.length) {
                    this.messageService.add({ severity: 'success', summary: 'Role', detail: 'Delete successfully!', life: 10000 });
                    this.selecteds = [];
                    this.loadData();
                  }
                } else {
                  this.messageService.add({ severity: 'error', summary: 'Role', detail: res.body.message, life: 10000 });
                }
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Role',
                  detail: res.body.error.status + ' ' + res.body.error.error,
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

  editForm(role: RoleModel) {
    this.isEditing = true;
    this.isDisplayCreate = true;
    this.newRole = Object.assign({}, role);
  }

  reset() {
    this.err = '';
    this.err_mess = '';
  }

  save() {
    this.isSaving = true;
    if (this.isEditing) {
      this.edit();
    } else {
      this.roleService.create(this.newRole, this.vimId).subscribe(
        res => {
          if (res.body.errorCode === '00') {
            this.loadData();
            this.messageService.add({ severity: 'success', summary: 'Role', detail: 'Create successfully!', life: 10000 });
            this.isDisplayCreate = false;
            this.isSaving = false;
          } else {
            this.messageService.add({ severity: 'error', summary: 'Role', detail: res.body.message, life: 10000 });
            this.isSaving = false;
          }
          this.isSaving = false;
        },
        res => {
          this.messageService.add({ severity: 'error', summary: 'Role', detail: res.body.message, life: 10000 });
          this.isSaving = false;
        }
      );
    }
  }

  edit() {
    this.roleService.update(this.newRole.name, this.newRole['id'], this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({ severity: 'success', summary: 'Role', detail: 'Update successfully!', life: 10000 });
          this.isEditing = false;
          this.isDisplayCreate = false;
          this.isSaving = false;
        } else {
          this.isSaving = false;
          this.messageService.add({ severity: 'error', summary: 'Role', detail: res.body.message, life: 10000 });
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({ severity: 'error', summary: 'Role', detail: res.body.message, life: 10000 });
        this.isSaving = false;
      }
    );
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
