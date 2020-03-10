import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { IVimInstance, VimInstanceModel, NewVimInstance, VimSubscriptionRequest } from 'app/shared/model/vimInstance.model';
import { ConfirmationService, MessageService } from 'primeng/api';
import { VimInstanceService } from 'app/entities/resource-management/vim-instance/vim-instance.service';
import { AccountService, LoginService } from 'app/core';
import { Router } from '@angular/router';
import { CommonUntil } from 'app/shared/util/commonUtil';
import { JhiEventManager } from 'ng-jhipster';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';

@Component({
  selector: 'jhi-vim-instance',
  templateUrl: './vim-instance.component.html',
  styleUrls: ['./vim-instance.component.scss']
})
export class VimInstanceComponent implements OnInit {
  @ViewChild('newIp', { static: true }) input: ElementRef;
  @ViewChild('dt', { static: true }) dt;

  commonUtil = new CommonUntil();
  controllerIps = [];
  selecteds = [];
  isCheckAll = false;
  validationStr = [];

  vimUser: string;
  newVimInstance = new NewVimInstance();
  data: IVimInstance[];
  first = 0;
  lastRowsPerPage = 10;

  isDisplayEditor = false;
  isSaving = false;

  ipV46 = /((^\s*((([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))\s*$)|(^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$))/;
  subnet: RegExp = /^(((255\.){3}(255|254|252|248|240|224|192|128|0+))|((255\.){2}(255|254|252|248|240|224|192|128|0+)\.0)|((255\.)(255|254|252|248|240|224|192|128|0+)(\.0+){2})|((255|254|252|248|240|224|192|128|0+)(\.0+){3}))(\/([0-9]|[1-2][0-9]|3[0-2]))$/;
  blockSpace: RegExp = /[^\s]/;

  location = {
    latitude: null,
    longitude: null,
    name: null
  };

  verifyVim = {
    username: null,
    password: null
  };

  isAdmin = false;

  constructor(
    private vimInstanceService: VimInstanceService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private accountService: AccountService,
    private loginService: LoginService,
    private router: Router,
    private eventManager: JhiEventManager,
    private cdr: ChangeDetectorRef
  ) {
    this.newVimInstance.type = null;
  }

  ngOnInit() {
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.loadData();
  }

  loadData() {
    this.vimInstanceService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
          const selectedTemp = [];
          this.selecteds.forEach((selected: IVimInstance) => {
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
            summary: 'Vim Instance',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Vim Instance',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  checkNumber(min: number, max: number, value: number) {
    return this.commonUtil.checkNumber(min, max, value);
  }

  save() {
    this.isSaving = true;

    this.newVimInstance.location = {};
    this.newVimInstance.location = this.location;
    this.newVimInstance.controllerPhysicalIps = this.controllerIps;

    this.vimInstanceService.create(this.newVimInstance).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({
            severity: 'error',
            summary: 'Vim Instance',
            detail: res.body.message,
            life: 10000
          });
          const vimID = res.body.data.id;
          this.vimInstanceService.validateVimUser(vimID, this.verifyVim.username, this.verifyVim.password).subscribe(
            response => {
              if (response.body.errorCode === '00') {
                const vimSubscription = new VimSubscriptionRequest();
                vimSubscription.filter.vimIds.push(vimID);
                this.messageService.add({
                  severity: 'success',
                  summary: 'Validate Vim User bhh',
                  detail: res.body.message,
                  life: 10000
                });
                this.vimInstanceService.subscribeVim(vimSubscription).subscribe(
                  subscriptionRes => {
                    if (subscriptionRes.body.errorCode === '00') {
                      this.router.navigate(['/vims', vimID, 'project']);
                      this.isSaving = false;
                      this.messageService.add({
                        severity: 'success',
                        summary: 'Subscribe Vim Instance',
                        detail: res.body.message,
                        life: 10000
                      });
                    } else {
                      this.isSaving = false;
                      this.messageService.add({
                        severity: 'error',
                        summary: 'Subscribe Vim Instance',
                        detail: res.body.message,
                        life: 10000
                      });
                    }
                  },
                  subscriptionRes => {
                    this.isSaving = false;
                    this.messageService.add({
                      severity: 'error',
                      summary: 'Vim Instance',
                      detail: subscriptionRes.error.status + ' ' + subscriptionRes.error.message,
                      life: 10000
                    });
                  }
                );
              } else {
                this.isSaving = false;
                this.messageService.add({ severity: 'error', summary: 'Validate Vim User', detail: response.body.message, life: 10000 });
              }
            },
            response => {
              this.isSaving = false;
              this.messageService.add({
                severity: 'error',
                summary: 'Vim Instance',
                detail: response.error.status + ' ' + response.error.message,
                life: 10000
              });
            }
          );
          this.isDisplayEditor = false;
          this.isSaving = false;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Vim Instance',
            detail: res.body.message,
            life: 10000
          });
          this.isSaving = false;
        }
        this.isSaving = false;
      },
      res => {
        this.isSaving = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Vim Instance',
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
        let objVimInfo,
          vimInfoKeys = [];
        if (localStorage.getItem('vimConnectionInfo')) {
          objVimInfo = JSON.parse(localStorage.getItem('vimConnectionInfo'));
          vimInfoKeys = Object.keys(objVimInfo);
        }
        if (id) {
          this.vimInstanceService.delete(id).subscribe(res => {
            if (res.body.errorCode === '00') {
              vimInfoKeys.forEach(key => {
                if (objVimInfo[key].vimId === id) {
                  delete objVimInfo[key];
                  localStorage.setItem('vimConnectionInfo', JSON.stringify(objVimInfo));
                }
              });
              this.selecteds = this.selecteds.filter((item: IVimInstance) => {
                return item.id !== id;
              });
              this.loadData();
              this.eventManager.broadcast({ name: 'logoutVim', content: id });
              this.messageService.add({
                severity: 'success',
                summary: 'Vim Instance',
                detail: 'Delete successfully!',
                life: 10000
              });
            } else {
              this.messageService.add({
                severity: 'error',
                summary: 'Vim Instance',
                detail: res.body.message,
                life: 10000
              });
            }
          });
        } else {
          if (this.selecteds) {
            let counter = 0;
            this.selecteds.forEach((vim: IVimInstance) => {
              this.vimInstanceService.delete(vim.id).subscribe(
                res => {
                  if (res.body.errorCode === '00') {
                    vimInfoKeys.forEach(key => {
                      if (objVimInfo[key].vimId === id) {
                        delete objVimInfo[key];
                      }
                    });
                    counter++;
                    if (counter === this.selecteds.length) {
                      localStorage.setItem('vimConnectionInfo', JSON.stringify(objVimInfo));
                      this.loadData();
                      this.selecteds = [];
                      this.messageService.add({
                        severity: 'success',
                        summary: 'Vim Instance',
                        detail: 'Delete successfully!',
                        life: 10000
                      });
                      this.eventManager.broadcast({ name: 'logoutVim', content: vim.id });
                    }
                  } else {
                    this.loadData();
                    this.selecteds = [];
                    this.messageService.add({
                      severity: 'error',
                      summary: 'Vim Instance',
                      detail: res.body.message,
                      life: 10000
                    });
                  }
                },
                res => {
                  this.messageService.add({
                    severity: 'error',
                    summary: 'Vim Instance',
                    detail: res.error.status + ' ' + res.error.message,
                    life: 10000
                  });
                }
              );
            });
          }
        }
      },
      reject: () => {}
    });
  }

  addIp(ip: string) {
    if (this.ipV46.test(ip)) {
      if (!this.controllerIps) {
        this.controllerIps = [];
      }
      this.controllerIps.push(this.input.nativeElement.value);
      this.input.nativeElement.value = '';
    } else {
      this.validationStr.push('login.messages.error.invalidControllerIP');
    }
  }

  checkLatLong() {
    if (this.location.latitude < -90 || this.location.latitude > 90) {
      this.validationStr.push('login.messages.error.invalidLatitude');
    }
    if (this.location.longitude < -180 || this.location.longitude > 180) {
      this.validationStr.push('login.messages.error.invalidLongitude');
    }
  }

  checkLoggedVim(vimId: string) {
    return this.accountService.getCurrentLoggedVimUser(vimId) ? ['/vims', vimId] : ['/vims', vimId, 'login'];
  }

  replaceSpaceUsername() {
    this.verifyVim.username = this.verifyVim.username.replace(/\s/g, '');
  }

  replaceSpacePassword() {
    this.verifyVim.password = this.verifyVim.password.replace(/\s/g, '');
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
