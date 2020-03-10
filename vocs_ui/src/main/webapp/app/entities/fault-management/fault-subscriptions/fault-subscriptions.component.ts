import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { FaultSubscription, IFaultSubscription } from 'app/shared/model/faultSubscriptions.model';
import { MessageService, ConfirmationService } from 'primeng/api';
import { FaultSubscriptionsService } from './fault-subscriptions.service';
import { AccountService } from 'app/core';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';
import { INsInstance } from 'app/shared/model/nsInstance.model';

@Component({
  selector: 'jhi-fault-subscriptions',
  templateUrl: './fault-subscriptions.component.html',
  styleUrls: ['./fault-subscriptions.component.scss']
})
export class FaultSubscriptionsComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  selecteds = [];
  data: IFaultSubscription[];
  newFaultSubscription = new FaultSubscription();
  nsInstances: INsInstance[];
  selectedNsInstances: INsInstance = null;
  commaRegex: RegExp = /,/g;

  isDisplay = false;
  isAdmin = false;
  isSaving = false;
  isCheckAll = false;
  first = 0;
  lastRowsPerPage = 10;

  notificationTypes = null;

  uri_regex: RegExp = /^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/)?[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/;
  blockSpace: RegExp = /[^\s]/;

  constructor(
    private faultSubscriptionsService: FaultSubscriptionsService,
    private confirmationService: ConfirmationService,
    private accountService: AccountService,
    private messageService: MessageService,
    private nsInstanceService: NsInstanceService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.newFaultSubscription.filter = {
      notificationTypes: [],
      nsInstanceSubscriptionFilter: {
        nsdIds: [],
        vnfdIds: [],
        pnfdIds: [],
        nsInstanceIds: [],
        nsInstanceNames: []
      }
    };

    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.loadData();
    this.listNsInstance();
  }

  loadData() {
    this.faultSubscriptionsService.query().subscribe(res => {
      this.data = res.body.data;
      const selectedTemp = [];
      this.selecteds.forEach((selected: IFaultSubscription) => {
        this.data.forEach(item => {
          if (selected.id === item.id) {
            selectedTemp.push(item);
          }
        });
      });
      this.selecteds = selectedTemp;
      this.check();
    });
  }

  listNsInstance() {
    this.nsInstanceService.query().subscribe(res => {
      this.nsInstances = res.body.data;
    });
  }

  confirmDelete(faultSubscriptionID?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (faultSubscriptionID) {
          this.faultSubscriptionsService.delete(faultSubscriptionID).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({
                  severity: 'success',
                  summary: 'Fault Subscriptions',
                  detail: 'Delete successfully!',
                  life: 10000
                });
                this.selecteds = this.selecteds.filter((item: IFaultSubscription) => {
                  return item.id !== faultSubscriptionID;
                });
                this.loadData();
              } else {
                this.messageService.add({ severity: 'error', summary: 'Fault Subscriptions', detail: res.body.message, life: 10000 });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Fault Subscriptions',
                detail: res.error.status + ' ' + res.error.error,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          this.selecteds.forEach((faultSubscription: IFaultSubscription) => {
            this.faultSubscriptionsService.delete(faultSubscription.id).subscribe(
              res => {
                if (res.body.errorCode === '00') {
                  count++;
                  if (count === this.selecteds.length) {
                    this.messageService.add({
                      severity: 'success',
                      summary: 'Fault Subscriptions',
                      detail: 'Delete successfully!',
                      life: 10000
                    });
                    this.selecteds = [];
                    this.loadData();
                  }
                } else {
                  this.messageService.add({ severity: 'error', summary: 'Fault Subscriptions', detail: res.body.message, life: 10000 });
                }
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Fault Subscriptions',
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

  save() {
    this.isSaving = true;

    this.newFaultSubscription.filter = {
      notificationTypes: [],
      nsInstanceSubscriptionFilter: {
        nsdIds: [],
        vnfdIds: [],
        pnfdIds: [],
        nsInstanceIds: [],
        nsInstanceNames: []
      }
    };

    this.newFaultSubscription.filter.notificationTypes.push(this.notificationTypes);

    this.newFaultSubscription.filter.nsInstanceSubscriptionFilter.nsInstanceIds.push(this.selectedNsInstances.id);
    this.newFaultSubscription.filter.nsInstanceSubscriptionFilter.nsInstanceNames.push(this.selectedNsInstances.nsInstanceName);
    this.newFaultSubscription.filter.nsInstanceSubscriptionFilter.nsdIds.push(this.selectedNsInstances.nsdId);

    this.selectedNsInstances.vnfInstances.forEach(element => {
      this.newFaultSubscription.filter.nsInstanceSubscriptionFilter.vnfdIds.push(element.vnfdId);
    });

    this.faultSubscriptionsService.create(this.newFaultSubscription).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({
            severity: 'success',
            summary: 'Fault Subscriptions',
            detail: 'Create successfully!',
            life: 10000
          });
          this.isDisplay = false;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Fault Subscriptions',
            detail: res.body.message,
            life: 10000
          });
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Fault Subscriptions',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
        this.isSaving = false;
      }
    );
  }

  replaceSpace() {
    this.newFaultSubscription.callbackUri = this.newFaultSubscription.callbackUri.replace(/\s/g, '');
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
