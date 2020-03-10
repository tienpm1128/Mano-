import { Component, OnInit, ViewChild } from '@angular/core';
import { IUserMano, NewUserMano, UserMano } from 'app/shared/model/userMano.model';
import { UserManoService } from './user-mano.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AccountService } from 'app/core';
import { PatternValidator, NgForm } from '@angular/forms';
import { TenantService } from 'app/entities/tenant/tenant.service';
import { ITenant } from 'app/shared/model/tenant.model';

@Component({
  selector: 'jhi-user-mano',
  templateUrl: './user-mano.component.html',
  styleUrls: ['./user-mano.component.scss']
})
export class UserManoComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;
  @ViewChild('addUserManoForm', { static: true }) addUserManoForm;

  selecteds = [];

  newUserMano = new NewUserMano();
  editUserMano = new NewUserMano();
  pattern = new PatternValidator();
  data: IUserMano[];
  tenants: ITenant[];

  idUserMano = '';
  confirmPassword = '';
  err = '';
  err_mess = '';

  isDisplayCreate = false;
  isDisplayEdit = false;
  isAdmin = false;
  isSaving = false;
  isCheckAll = false;

  blockSpace: RegExp = /[^\s]/;
  password_regex: RegExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#()'"\=\-\\\[\]~^|_/.,:;+])[A-Za-z\d@$!%*?&#()'"\=\-\\\[\]~^|_/.,:;+]{2,25}$/;

  constructor(
    private userManoService: UserManoService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private accountService: AccountService,
    private tenantService: TenantService
  ) {}

  ngOnInit() {
    this.loadUserMano();
    this.loadTenant();
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');

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

  loadUserMano() {
    this.userManoService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
        } else {
          this.messageService.add({ severity: 'error', summary: 'User Mano', detail: res.body.message, life: 10000 });
        }
      },
      res => {
        this.messageService.add({ severity: 'error', summary: 'User Mano', detail: res.error.status + ' ' + res.error.error, life: 10000 });
      }
    );
  }

  loadTenant() {
    this.tenantService.query().subscribe(res => {
      this.tenants = res.body.data;
    });
  }

  confirmDelete(userManoID?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (userManoID) {
          this.userManoService.delete(userManoID).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'User Mano', detail: 'Delete successfully!', life: 10000 });
                this.selecteds = [];
                this.loadUserMano();
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
        } else {
          let count = 0;
          this.selecteds.forEach((user: IUserMano) => {
            this.userManoService.delete(user.id).subscribe(
              res => {
                if (res.body.errorCode === '00') {
                  count++;
                  if (count === this.selecteds.length) {
                    this.messageService.add({ severity: 'success', summary: 'User Mano', detail: 'Delete successfully!', life: 10000 });
                    this.selecteds = [];
                    this.loadUserMano();
                  }
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
          });
        }
      },
      reject: () => {}
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
    // this.newUserMano = Object.assign({}, userMano);
  }

  reset() {
    this.addUserManoForm.reset();
    this.err_mess = '';
    this.err = '';
  }

  edit() {
    this.userManoService.update(this.editUserMano, this.idUserMano).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadUserMano();
          this.messageService.add({
            severity: 'success',
            summary: 'User Mano',
            detail: 'Update successfully!',
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
          this.isSaving = false;
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

  save() {
    this.isSaving = true;

    if (this.newUserMano.manoUserPassword === this.confirmPassword) {
      this.err_mess = '';
      this.userManoService.create(this.newUserMano).subscribe(
        res => {
          if (res.body.errorCode === '00') {
            this.loadUserMano();
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
            this.isSaving = false;
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

  check() {
    const currentItem = this.dt.first + 1 + this.dt._rows > this.dt.totalRecords ? this.dt.totalRecords - this.dt.first : this.dt._rows;
    this.isCheckAll = this.selecteds.length !== 0;
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
      this.selecteds = this.dt.filteredValue;
    }
  }

  replaceSpaceUsername() {
    this.newUserMano.manoUsername = this.newUserMano.manoUsername.replace(/\s/g, '');
  }

  replaceSpacePassword() {
    this.newUserMano.manoUserPassword = this.newUserMano.manoUserPassword.replace(/\s/g, '');
  }

  replaceSpaceConfirmPassword() {
    this.confirmPassword = this.confirmPassword.replace(/\s/g, '');
  }
}
