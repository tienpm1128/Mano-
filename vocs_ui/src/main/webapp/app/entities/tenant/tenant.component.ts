import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { TenantService } from './tenant.service';
import { MessageService, ConfirmationService } from 'primeng/api';
import { AccountService } from 'app/core';
import { ITenant } from 'app/shared/model/tenant.model';
import { UserManoComponent } from '../resource-management/user-mano/user-mano.component';
import { NewUserMano, UserMano } from 'app/shared/model/userMano.model';
import { PatternValidator } from '@angular/forms';
import { IProjects } from 'app/shared/model/projects.model';

@Component({
  selector: 'jhi-tenant',
  templateUrl: './tenant.component.html',
  styleUrls: ['./tenant.component.scss']
})
export class TenantComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;
  @ViewChild('addUserManoForm', { static: true }) addUserManoForm;

  userMano: UserManoComponent;

  selecteds = [];

  data: ITenant[];
  newUserMano = new NewUserMano();
  editUserMano = new NewUserMano();
  pattern = new PatternValidator();

  idUserMano = '';
  confirmPassword = '';
  err = '';
  err_mess = '';
  first = 0;
  lastRowsPerPage = 10;

  newTenants = {
    tenantName: '',
    tenantDescription: ''
  };

  isDisplayTenant = false;
  isDisplayCreate = false;
  isDisplayEdit = false;
  isAdmin = false;
  isTenant = false;
  isSaving = false;
  isCheckAll = false;

  blockSpace: RegExp = /[^\s]/;
  password_regex: RegExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#()'"\=\-\\\[\]~^|_/.,:;+])[A-Za-z\d@$!%*?&#()'"\=\-\\\[\]~^|_/.,:;+]{2,25}$/;

  constructor(
    private tenantService: TenantService,
    private accountService: AccountService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.loadData();

    this.newUserMano.manoUserRoles = [
      {
        roleType: null
      }
    ];

    this.editUserMano.manoUserRoles = [
      {
        roleType: null
      }
    ];

    this.newUserMano.assignedTenant = null;
  }

  loadData() {
    this.tenantService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
          const selectedTemp = [];
          this.selecteds.forEach((selected: ITenant) => {
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
            summary: 'Tenant',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Tenant',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  confirmDelete(id?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (id) {
          this.tenantService.delete(id).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'Tenant', detail: 'Delete successfully!', life: 10000 });
                this.selecteds = this.selecteds.filter((item: ITenant) => {
                  return item.id !== id;
                });
                this.loadData();
              } else {
                this.messageService.add({ severity: 'error', summary: 'Tenant', detail: res.body.message, life: 10000 });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Tenant',
                detail: res.error.status + ' ' + res.error.error,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          this.selecteds.forEach((tenant: ITenant) => {
            this.tenantService.delete(tenant.id).subscribe(
              res => {
                if (res.body.errorCode === '00') {
                  count++;
                  if (count === this.selecteds.length) {
                    this.messageService.add({ severity: 'success', summary: 'Tenant', detail: 'Delete successfully!', life: 10000 });
                    this.selecteds = [];
                    this.loadData();
                  }
                } else {
                  this.messageService.add({ severity: 'error', summary: 'Tenant', detail: res.body.message, life: 10000 });
                }
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Tenant',
                  detail: res.error.status + ' ' + res.error.error,
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

  confirmDeleteUserMano(userManoID?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        this.tenantService.deleteUserMano(userManoID).subscribe(
          res => {
            if (res.body.errorCode === '00') {
              this.messageService.add({ severity: 'success', summary: 'User Mano', detail: 'Delete successfully!', life: 10000 });
              this.loadData();
            } else {
              this.messageService.add({ severity: 'error', summary: 'User Mano', detail: res.body.message, life: 10000 });
            }
          },
          res => {
            this.messageService.add({
              severity: 'error',
              summary: 'User Mano',
              detail: res.error.status + ' ' + res.error.error,
              life: 10000
            });
          }
        );
      }
    });
  }

  editForm(userMano: UserMano) {
    this.isDisplayEdit = true;
    this.idUserMano = userMano.id;
    this.editUserMano.manoUsername = userMano.username;
    this.editUserMano.manoUserRoles = [
      {
        roleType: userMano.roles[0].roleType
      }
    ];
  }

  reset() {
    this.addUserManoForm.reset();
    this.err_mess = '';
    this.err = '';
  }

  save() {
    this.isSaving = true;

    this.tenantService.create(this.newTenants).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({
            severity: 'success',
            summary: 'Tenant',
            detail: 'Create successfully!',
            life: 10000
          });
          this.isDisplayTenant = false;
          this.isSaving = false;
        } else {
          this.isSaving = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Tenant',
            detail: res.body.message,
            life: 10000
          });
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Tenant',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
        this.isSaving = false;
      }
    );
  }

  saveUserMano() {
    this.isSaving = true;

    if (this.newUserMano.manoUserPassword === this.confirmPassword) {
      this.err_mess = '';
      this.tenantService.createUserMano(this.newUserMano).subscribe(
        res => {
          if (res.body.errorCode === '00') {
            this.loadData();
            this.messageService.add({
              severity: 'success',
              summary: 'User Mano',
              detail: 'Create successfully!',
              life: 10000
            });
            this.isDisplayCreate = false;
            this.isSaving = false;
          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'User Mano',
              detail: res.body.message,
              life: 10000
            });
          }
          this.isSaving = false;
        },
        res => {
          this.messageService.add({
            severity: 'error',
            summary: 'User Mano',
            detail: res.error.status + ' ' + res.error.message,
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

  edituserMano() {
    this.tenantService.updateUserMano(this.editUserMano, this.idUserMano).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({
            severity: 'success',
            summary: 'User Mano',
            detail: 'Update successfully!',
            life: 10000
          });
          this.isDisplayEdit = false;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'User Mano',
            detail: res.body.message,
            life: 10000
          });
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'User Mano',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
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
    const currentItem = this.dt.first + 1 + this.dt._rows > this.data.length ? this.data.length - this.dt.first : this.dt._rows;
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
