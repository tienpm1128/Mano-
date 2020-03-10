import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { ISubscriptions, Subscriptions } from 'app/shared/model/subscriptions.model';
import { HpPmSubscriptionsService } from './hp-pm-subscriptions.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AccountService, StateStorageService } from 'app/core';
import { IPmJobs } from 'app/shared/model/pmjobs.model';
import { IThreshold } from 'app/shared/model/threshold.model';
import { LocalStorageService } from 'ngx-webstorage';
import { ISubscriptionsVim, SubscriptionsVim } from 'app/shared/model/subcriptions.vim.model';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.scss']
})
export class VimSubscriptionsComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  selecteds = [];
  isCheckAll = false;
  selectHypervisor = null;

  commaRegex: RegExp = /,/g;
  data: ISubscriptionsVim[];
  newVimSubscriptions: ISubscriptionsVim;
  pmJobs: IPmJobs[];
  thresholds: IThreshold[];

  hypervisors = [];
  first = 0;
  lastRowsPerPage = 10;
  nsInstanceId: string[];

  isDisplay = false;
  isAdmin = false;
  isSaving = false;

  uri_regex: RegExp = /^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/)?[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/;
  blockSpace: RegExp = /[^\s]/;

  constructor(
    private vimSubscriptionsService: HpPmSubscriptionsService,
    private confirmationService: ConfirmationService,
    private accountService: AccountService,
    private messageService: MessageService,
    private localStorageService: LocalStorageService,
    private stateStorageService: StateStorageService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {
    if (this.localStorageService.retrieve('hypervisors')) {
      this.hypervisors = this.localStorageService.retrieve('hypervisors');
    }
  }

  ngOnInit() {
    this.newVimSubscriptions = new SubscriptionsVim();
    this.newVimSubscriptions.filter = {
      computeFilters: [
        {
          computeId: '',
          computeIp: ''
        }
      ]
    };

    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.loadData();
  }

  loadData() {
    this.vimSubscriptionsService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
          this.data.forEach(vimSub => {
            this.vimSubscriptionsService.getDetailPmJob(vimSub.id).subscribe(
              pm => {
                if (pm.body.errorCode === '00') {
                  vimSub['pmJobDetail'] = pm.body.data;
                } else {
                  this.messageService.add({
                    severity: 'error',
                    summary: 'PM Job',
                    detail: pm.body.message,
                    life: 10000
                  });
                }
              },
              pm => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'PM Job',
                  detail: pm.error.status + ' ' + pm.error.message,
                  life: 10000
                });
              }
            );

            this.vimSubscriptionsService.getDetailThreshold(vimSub.id).subscribe(
              threshold => {
                if (threshold.body.errorCode === '00') {
                  vimSub['thresholdDetail'] = threshold.body.data;
                } else {
                  this.messageService.add({
                    severity: 'error',
                    summary: 'Threshold',
                    detail: threshold.body.message,
                    life: 10000
                  });
                }
              },
              threshold => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Threshold',
                  detail: threshold.error.status + ' ' + threshold.error.message,
                  life: 10000
                });
              }
            );
          });
          this.check();
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Subscription',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Subscription',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  save() {
    this.newVimSubscriptions.filter.computeFilters[0].computeId = this.selectHypervisor.id;
    this.newVimSubscriptions.filter.computeFilters[0].computeIp = this.selectHypervisor.hostIP;

    this.isSaving = true;

    this.vimSubscriptionsService.create(this.newVimSubscriptions).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({ severity: 'success', summary: 'Subscriptions', detail: 'Create successfully!', life: 10000 });
          this.isDisplay = false;
          this.isSaving = false;
        } else {
          this.messageService.add({ severity: 'error', summary: 'Subscriptions', detail: res.body.message, life: 10000 });
          this.isSaving = false;
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Subscriptions',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
        this.isSaving = false;
      }
    );
  }

  confirmDelete(id?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (id) {
          this.vimSubscriptionsService.delete(id).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'Subscriptions', detail: 'Delete successfully!', life: 10000 });
                this.selecteds = [];
                this.selecteds = this.selecteds.filter((item: ISubscriptions) => {
                  return item.id !== id;
                });
                this.loadData();
              } else {
                this.messageService.add({ severity: 'error', summary: 'Subscriptions', detail: res.body.message, life: 10000 });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Subscriptions',
                detail: res.error.status + ' ' + res.error.error,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          this.selecteds.forEach((nsSubscription: Subscriptions) => {
            this.vimSubscriptionsService.delete(nsSubscription.id).subscribe(
              res => {
                if (res.body.errorCode === '00') {
                  count++;
                  if (count === this.selecteds.length) {
                    this.messageService.add({ severity: 'success', summary: 'Subscriptions', detail: 'Delete successfully!', life: 10000 });
                    this.selecteds = [];
                    this.loadData();
                  }
                } else {
                  this.messageService.add({ severity: 'error', summary: 'Subscriptions', detail: res.body.message, life: 10000 });
                }
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Subscriptions',
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

  replaceSpace() {
    this.newVimSubscriptions.callbackUri = this.newVimSubscriptions.callbackUri.replace(/\s/g, '');
  }

  toLoginVim() {
    this.stateStorageService.storeUrl(this.router.url);
    this.router.navigate(['/vims']);
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
