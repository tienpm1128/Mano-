import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { INsInstance, NsInstance, NewNsInstance } from 'app/shared/model/nsInstance.model';
import { ConfirmationService, MessageService } from 'primeng/api';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';
import { INsd } from 'app/shared/model/nsd.model';
import { NsdManagementService } from 'app/entities/network-service/nsd-management/nsd-management.service';
import { AccountService } from 'app/core';
import { Subscriptions } from 'app/shared/model/subscriptions.model';
import { NsInstanceSubscriptionService } from '../ns-instance-subscription/ns-instance-subscription.service';
import { FaultSubscriptionsService } from 'app/entities/fault-management/fault-subscriptions/fault-subscriptions.service';
import { NsPmSubscriptionsService } from 'app/entities/performance/ns/subscriptions/ns-pm-subscriptions.service';

@Component({
  selector: 'jhi-ns-instance',
  templateUrl: './ns-instance.component.html',
  styleUrls: ['./ns-instance.component.scss']
})
export class NsInstanceComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  isCheckAll = false;
  selecteds = [];
  selectedNsd: INsd = null;
  lastRowsPerPage = 10;
  first = 0;

  nsds: INsd[];
  data: INsInstance[];

  newNsInstance = new NewNsInstance();

  // Data request để tạo mới subscriptions (1.2.23) - (1.4.5) - (1.5.11)
  newSubscriptions = new Subscriptions();

  isDisplayCreate = false;
  isAdmin = false;
  hasInstantiated = false;
  isSaving = false;

  constructor(
    private nsInstanceService: NsInstanceService,
    private nsdManagementService: NsdManagementService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private accountService: AccountService,
    private nsInstanceSubscriptionService: NsInstanceSubscriptionService,
    private faultSubscriptionsService: FaultSubscriptionsService,
    private nsPmSubscriptionsService: NsPmSubscriptionsService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.loadData();
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.nsdManagementService.query().subscribe(res => {
      this.nsds = res.body.data;
    });

    this.newSubscriptions.filter = {
      nsInstanceSubscriptionFilter: {
        nsdIds: [],
        vnfdIds: [],
        pnfdIds: [],
        nsInstanceIds: [],
        nsInstanceNames: []
      }
    };
    this.selectedNsd = null;
  }

  loadData() {
    this.nsInstanceService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
          this.data.forEach(nsInstance => {
            if (nsInstance.nsState === 'INSTANTIATED') {
              this.hasInstantiated = true;
            }
          });
          const selectedTemp = [];
          this.selecteds.forEach((selected: INsInstance) => {
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
            summary: 'Ns Instance',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Ns Instance',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  confirmDelete(nsInstanceId?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (nsInstanceId) {
          this.nsInstanceService.delete(nsInstanceId).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'Ns Instance', detail: 'Delete successfully!' });
                this.selecteds = this.selecteds.filter((item: INsInstance) => {
                  return item.id !== nsInstanceId;
                });
                this.loadData();
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Ns Instance',
                  detail: res.body.message,
                  life: 10000
                });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Ns Instance',
                detail: res.error.status + ' ' + res.error.message,
                life: 10000
              });
            }
          );
        } else if (this.selecteds) {
          let count = 0;
          this.selecteds.forEach(nsd => {
            this.nsInstanceService.delete(nsd.id).subscribe(res => {
              count++;
              if (count === this.selecteds.length) {
                this.selecteds = [];
                this.messageService.add({ severity: 'success', summary: 'Ns Instance', detail: 'Delete successfully!', life: 10000 });
                this.loadData();
              }
              if (res.body.errorCode !== '00') {
                this.messageService.add({ severity: 'error', summary: 'Ns Instance', detail: res.body.message, life: 10000 });
              }
            });
          });
        }
      },
      reject: () => {}
    });
  }

  save() {
    this.isSaving = true;
    this.newNsInstance.nsdId = this.selectedNsd.nsdId;

    this.nsInstanceService.create(this.newNsInstance).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.newSubscriptions.filter.nsInstanceSubscriptionFilter.nsInstanceIds.push(res.body.data.id);
          this.newSubscriptions.filter.nsInstanceSubscriptionFilter.nsInstanceNames.push(res.body.data.nsInstanceName);
          this.newSubscriptions.filter.nsInstanceSubscriptionFilter.nsdIds.push(res.body.data.nsdId);
          this.messageService.add({ severity: 'success', summary: 'Ns Instance', detail: 'Create successfully!', life: 10000 });
          this.isDisplayCreate = false;
          this.loadData();
          /*this.nsInstanceSubscriptionService.create(this.newSubscriptions).subscribe(
            nsi => {
              if (nsi.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'Ns Instance Subscription', detail: 'Create successfully!' });
                this.faultSubscriptionsService.create(this.newSubscriptions).subscribe(
                  fault => {
                    if (fault.body.errorCode === '00') {
                      this.messageService.add({ severity: 'success', summary: 'Fault Subscription', detail: 'Create successfully!' });
                      this.nsPmSubscriptionsService.create(this.newSubscriptions).subscribe(
                        create => {
                          if (create.body.errorCode === '00') {
                            this.loadData();
                            this.messageService.add({ severity: 'success', summary: 'Ns Pm Subscription', detail: 'Create successfully!' });
                            this.isSaving = false;
                          } else {
                            this.messageService.add({ severity: 'error', summary: 'Ns Pm Subscription', detail: create.body.message });
                            this.isSaving = false;
                          }
                          this.isSaving = false;
                        },
                        create => {
                          this.isSaving = false;
                          this.messageService.add({
                            severity: 'error',
                            summary: 'Ns Pm Subscription',
                            detail: create.error.status + ' ' + create.error.message
                          });
                        }
                      );
                    } else {
                      this.isSaving = false;
                      this.messageService.add({ severity: 'error', summary: 'Fault Subscription', detail: fault.body.message });
                    }
                    this.isSaving = false;
                  },
                  fault => {
                    this.isSaving = false;
                    this.messageService.add({
                      severity: 'error',
                      summary: 'Fault Subscription',
                      detail: fault.error.status + ' ' + fault.error.message
                    });
                  }
                );
              } else {
                this.isSaving = false;
                this.messageService.add({ severity: 'error', summary: 'Ns Instance', detail: nsi.body.message });
              }
              this.isSaving = false;
            },
            nsi => {
              this.isSaving = false;
              this.messageService.add({ severity: 'error', summary: 'Ns Instance', detail: nsi.error.status + ' ' + nsi.error.message });
            }
          );*/
        } else {
          this.messageService.add({ severity: 'error', summary: 'Ns Instance', detail: res.body.message, life: 10000 });
        }
        this.isSaving = false;
      },
      res => {
        this.isSaving = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Ns Instance',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  check() {
    const currentItem = this.dt.first + 1 + this.dt._rows > this.data.length ? this.data.length - this.dt.first : this.dt._rows;
    !this.dt.filteredValue || (this.dt.filteredValue && this.dt.filteredValue.length > 0)
      ? (this.isCheckAll = this.selecteds.length !== 0)
      : (this.isCheckAll = false);
    if (!this.dt.filteredValue) {
      let changed = false;
      for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
        if (this.data[i].nsState === 'INSTANTIATED') {
          continue;
        }
        changed = true;
        if (!this.selecteds.includes(this.data[i])) {
          this.isCheckAll = false;
          break;
        }
      }
      if (changed === false) {
        this.isCheckAll = false;
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
          if (this.data[i].nsState !== 'INSTANTIATED') {
            this.selecteds = [...this.selecteds, this.data[i]];
          }
        }
      } else {
        for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
          this.selecteds = this.selecteds.filter(selected => {
            return this.data[i] !== selected;
          });
        }
      }
    } else {
      this.isCheckAll ? (this.selecteds = this.dt.filteredValue.filter(item => item.nsState !== 'INSTANTIATED')) : (this.selecteds = []);
    }
  }

  onSortOrPage() {
    if (this.dt.filteredValue && this.dt.filteredValue.length === 0) {
      this.isCheckAll = false;
    } else {
      const currentItem = this.dt.first + 1 + this.dt._rows > this.dt.totalRecords ? this.dt.totalRecords - this.dt.first : this.dt._rows;
      const notCheckables = this.data
        .slice(this.dt.first, currentItem + this.dt.first)
        .filter(notCheck => notCheck.nsState === 'INSTANTIATED').length;
      this.isCheckAll = true;

      for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
        if ((!this.selecteds.includes(this.data[i]) && this.data[i].nsState !== 'INSTANTIATED') || notCheckables === currentItem) {
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
