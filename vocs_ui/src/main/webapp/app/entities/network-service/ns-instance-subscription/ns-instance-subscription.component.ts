import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { NsInstanceSubscriptionService } from 'app/entities/network-service/ns-instance-subscription/ns-instance-subscription.service';
import { ILccnSubscription } from 'app/shared/model/lccnSubscription.model';
import { AccountService } from 'app/core';
import { INsd } from 'app/shared/model/nsd.model';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';

@Component({
  selector: 'jhi-ns-instance-subscription',
  templateUrl: './ns-instance-subscription.component.html',
  styleUrls: ['./ns-instance-subscription.component.scss']
})
export class NsInstanceSubscriptionComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  selecteds = [];
  isCheckAll = false;
  data: ILccnSubscription[];
  isAdmin = false;
  commaRegex: RegExp = /,/g;

  first = 0;
  lastRowsPerPage = 10;

  constructor(
    private nsInstanceSubscriptionService: NsInstanceSubscriptionService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private accountService: AccountService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.loadData();
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
  }

  loadData() {
    this.nsInstanceSubscriptionService.query().subscribe(res => {
      if (res.body.errorCode === '00') {
        this.data = res.body.data;
        const selectedTemp = [];
        this.selecteds.forEach((selected: ILccnSubscription) => {
          this.data.forEach(item => {
            if (selected.id === item.id) {
              selectedTemp.push(item);
            }
          });
        });
        this.selecteds = selectedTemp;
        this.check();
      }
    });
  }

  confirmDelete(nsInstanceId?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (nsInstanceId) {
          this.nsInstanceSubscriptionService.delete(nsInstanceId).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.selecteds = this.selecteds.filter((item: ILccnSubscription) => {
                  return item.id !== nsInstanceId;
                });
                this.loadData();
                this.messageService.add({
                  severity: 'success',
                  summary: 'Ns Instance subscription',
                  detail: 'Delete successfully!',
                  life: 10000
                });
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Ns Instance subscription',
                  detail: res.body.message,
                  life: 10000
                });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Ns Instance subscription',
                detail: res.error.status + ' ' + res.error.message,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          this.selecteds.forEach(nsis => {
            this.nsInstanceSubscriptionService.delete(nsis.id).subscribe(res => {
              if (res.body.errorCode === '00') {
                count++;
                if (count === this.selecteds.length) {
                  this.selecteds = [];
                  this.messageService.add({
                    severity: 'Success',
                    summary: 'Ns Instance subscription',
                    detail: 'Delete successfully!',
                    life: 10000
                  });
                  this.loadData();
                }
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Ns Instance subscription',
                  detail: res.body.message,
                  life: 10000
                });
              }
            });
          });
        }
      },
      reject: () => {}
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
