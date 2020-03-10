import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { ISubscriptions, Subscriptions } from 'app/shared/model/subscriptions.model';
import { NsPmSubscriptionsService } from './ns-pm-subscriptions.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AccountService } from 'app/core';
import { IPmJobs } from 'app/shared/model/pmjobs.model';
import { IThreshold } from 'app/shared/model/threshold.model';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';
import { INsInstance } from 'app/shared/model/nsInstance.model';

@Component({
  selector: 'jhi-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.scss']
})
export class SubscriptionsComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  selecteds = [];
  isCheckAll = false;

  commaRegex: RegExp = /,/g;
  data: ISubscriptions[];
  newNsSubscriptions: ISubscriptions = new Subscriptions();
  selectNsInstance = [];
  pmJobs: IPmJobs[];
  thresholds: IThreshold[];
  listNsInstances: INsInstance[];
  selectedNsInstances: INsInstance = null;

  nsInstanceId: string[];

  isDisplay = false;
  isAdmin = false;
  isSaving = false;
  first = 0;
  lastRowsPerPage = 10;

  uri_regex: RegExp = /^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/)?[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/;
  blockSpace: RegExp = /[^\s]/;

  constructor(
    private nsSubscriptionsService: NsPmSubscriptionsService,
    private confirmationService: ConfirmationService,
    private accountService: AccountService,
    private messageService: MessageService,
    private nsInstanceService: NsInstanceService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.loadData();
    this.loadNsInstance();

    this.newNsSubscriptions.filter = {
      nsInstanceSubscriptionFilter: {
        nsdIds: [null],
        vnfdIds: [],
        pnfdIds: [],
        nsInstanceIds: [],
        nsInstanceNames: []
      },
      notificationTypes: []
    };
  }

  loadData() {
    this.nsSubscriptionsService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
          this.data.forEach(nsSub => {
            if (nsSub.filter['notificationTypes']) {
              nsSub.filter['notificationTypes'].forEach(type => {
                switch (type) {
                  case 'PerformanceInformationAvailableNotification':
                    this.nsSubscriptionsService.getDetailPmJob(nsSub.filter['nsInstanceSubscriptionFilter'].nsInstanceIds[0]).subscribe(
                      pm => {
                        if (pm.body.errorCode === '00') {
                          nsSub['pmJobDetail'] = pm.body.data;
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
                    break;
                  case 'ThresholdCrossedNotification':
                    this.nsSubscriptionsService.getDetailThreshold(nsSub.filter['nsInstanceSubscriptionFilter'].nsInstanceIds[0]).subscribe(
                      threshold => {
                        if (threshold.body.errorCode === '00') {
                          nsSub['thresholdDetail'] = threshold.body.data;
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
                    break;
                  default:
                    break;
                }
              });
            }
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

  loadNsInstance() {
    this.nsInstanceService.query().subscribe(res => {
      this.listNsInstances = res.body.data;
    });
  }

  loadDetailPmJob(nsSubscriptions: ISubscriptions[]) {
    nsSubscriptions.forEach(row => {
      this.nsInstanceId = row.filter.nsInstanceSubscriptionFilter.nsInstanceIds;
      this.nsSubscriptionsService.getDetailPmJob(this.nsInstanceId).subscribe(res => {
        this.pmJobs.push(res.body.data);
      });
    });
  }

  loadDetailThreshold(nsSubscriptions: ISubscriptions[]) {
    nsSubscriptions.forEach(row => {
      this.nsInstanceId = row.filter.nsInstanceSubscriptionFilter.nsInstanceIds;
      this.nsSubscriptionsService.getDetailThreshold(this.nsInstanceId).subscribe(res => {
        this.thresholds.push(res.body.data);
      });
    });
  }

  save() {
    // push request body

    this.selectNsInstance.forEach(row => {
      this.newNsSubscriptions.filter.nsInstanceSubscriptionFilter.nsInstanceIds.push(row.id);
      this.newNsSubscriptions.filter.nsInstanceSubscriptionFilter.nsInstanceNames.push(row.nsInstanceName);
      this.newNsSubscriptions.filter.nsInstanceSubscriptionFilter.nsdIds.push(row.nsdId);

      row.vnfInstances.forEach(r => {
        this.newNsSubscriptions.filter.nsInstanceSubscriptionFilter.vnfdIds.push(r.vnfdId);
      });
    });

    this.isSaving = true;

    this.nsSubscriptionsService.create(this.newNsSubscriptions).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({ severity: 'success', summary: 'Subscriptions', detail: 'Create successfully!', life: 10000 });
          this.isDisplay = false;
          this.isSaving = false;
        } else {
          this.isSaving = false;
          this.messageService.add({ severity: 'error', summary: 'Subscriptions', detail: res.body.message, life: 10000 });
        }
        this.isSaving = false;
      },
      res => {
        this.isSaving = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Subscriptions',
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
          this.nsSubscriptionsService.delete(id).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'Subscriptions', detail: 'Delete successfully!', life: 10000 });
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
            this.nsSubscriptionsService.delete(nsSubscription.id).subscribe(
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

  resetForm() {
    this.newNsSubscriptions.filter.nsInstanceSubscriptionFilter.nsInstanceIds = [];
    this.newNsSubscriptions.filter.notificationTypes[0] = null;
    this.newNsSubscriptions.callbackUri = null;
  }

  replaceSpace() {
    this.newNsSubscriptions.callbackUri = this.newNsSubscriptions.callbackUri.replace(/\s/g, '');
  }

  check() {
    const currentItem = this.dt.first + 1 + this.dt._rows > this.dt.totalRecords ? this.dt.totalRecords - this.dt.first : this.dt._rows;
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
