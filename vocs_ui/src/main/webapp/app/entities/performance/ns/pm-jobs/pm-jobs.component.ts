import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { IPmJobs, PmJobs } from 'app/shared/model/pmjobs.model';
import { PmJobsService } from './pm-jobs.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AccountService } from 'app/core';
import { CommonUntil } from 'app/shared/util/commonUtil';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';
import { INsInstance } from 'app/shared/model/nsInstance.model';
import { INsSubscription } from 'app/shared/model/nsSubscription.model';

@Component({
  selector: 'jhi-pm-jobs',
  templateUrl: './pm-jobs.component.html',
  styleUrls: ['./pm-jobs.component.scss']
})
export class PmJobsComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  selecteds = [];
  isCheckAll = false;
  selectedPerformanceMetricGroup: string;

  data: IPmJobs[];
  newPmjobs = new PmJobs();
  nsInstances: INsInstance[];

  reportingBoundary: string;
  today = new Date();

  isDisplay = false;
  isAdmin = false;
  isSaving = false;
  isFutureDate = true;
  first = 0;
  lastRowsPerPage = 10;

  commonUtil = new CommonUntil();
  commaRegex: RegExp = /,/g;

  performanceMetrics = [
    'node_cpu_seconds_total_mode_idle',
    'node_cpu_seconds_total_mode_iowait',
    'node_cpu_seconds_total_mode_irq',
    'node_cpu_seconds_total_mode_nice',
    'node_cpu_seconds_total_mode_steal',
    'node_cpu_seconds_total_mode_system',
    'node_cpu_seconds_total_mode_user',
    'node_cpu_seconds_total_mode_usage',
    'node_memory_MemAvailable_bytes',
    'NODE_MEMORY_MEMTOTAL_BYTES',
    'NODE_MEMORY_CACHED_BYTES',
    'NODE_MEMORY_BUFFERS_BYTES',
    'NODE_MEMORY_HUGEPAGES_FREE',
    'NODE_MEMORY_HUGEPAGESIZE_BYTES',
    'NODE_MEMORY_HUGEPAGES_TOTAL',
    'AVG_MEMORY_MEMAVAILABLE_BYTES',
    'AVG_MEMORY_CACHED_BYTES',
    'AVG_MEMORY_BUFFERS_BYTES',
    'AVG_MEMORY_HUGEPAGES_FREE',
    'node_storage_usage_size_bytes',
    'node_filesystem_size_bytes',
    'node_filesystem_free_bytes',
    'node_disk_read_bytes_total',
    'node_disk_written_bytes_total',
    'node_network_transmit_bytes_total',
    'node_network_transmit_bytes_total',
    'node_network_packets_bytes_total',
    'node_network_transmit_packets_total',
    'node_network_transmit_bytes_total',
    'node_network_transmit_bytes_total',
    'node_network_packets_bytes_total',
    'node_network_transmit_packets_total',
    'node_hwon_temp_celsius'
  ];
  performanceMetricGroups = ['CPU', 'MEMORY', 'DISK', 'NETWORK', 'TEMPERATURE'];

  constructor(
    private pmJobService: PmJobsService,
    private confirmationService: ConfirmationService,
    private accountService: AccountService,
    private messageService: MessageService,
    private nsInstanceService: NsInstanceService,
    private cdr: ChangeDetectorRef
  ) {
    this.newPmjobs.objectInstanceIds = [];
    this.newPmjobs.criteria = {
      performanceMetric: [],
      performanceMetricGroup: [],
      collectionPeriod: null,
      reportingPeriod: null,
      reportingBoundary: null
    };
  }

  ngOnInit() {
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.newPmjobs.objectInstanceIds[0] = null;
    this.loadData();
    this.loadNsInstance();
  }

  loadData() {
    this.pmJobService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
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
            summary: 'PM Job',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'PM Job',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  loadNsInstance() {
    this.nsInstanceService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.nsInstances = res.body.data;
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

  checkNumber(min: number, max: number, value: any) {
    if (value) {
      this.isSaving = !!this.commonUtil.checkNumber(min, max, value);
      if (value !== undefined) {
        return this.commonUtil.checkNumber(min, max, value);
      }
    }
  }

  confirmDelete(id?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (id) {
          this.pmJobService.delete(id).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'Pm Jobs', detail: 'Delete successfully!', life: 10000 });
                this.selecteds = this.selecteds.filter((item: IPmJobs) => {
                  return item.id !== id;
                });
                this.loadData();
              } else {
                this.messageService.add({ severity: 'error', summary: 'Pm Jobs', detail: res.body.message, life: 10000 });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Pm Jobs',
                detail: res.error.status + ' ' + res.error.error,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          this.selecteds.forEach((pmJob: IPmJobs) => {
            this.pmJobService.delete(pmJob.id).subscribe(
              res => {
                if (res.body.errorCode === '00') {
                  count++;
                  if (count === this.selecteds.length) {
                    this.messageService.add({ severity: 'success', summary: 'Pm Jobs', detail: 'Delete successfully!', life: 10000 });
                    this.selecteds = [];
                    this.loadData();
                  }
                } else {
                  this.messageService.add({ severity: 'error', summary: 'Pm Jobs', detail: res.body.message, life: 10000 });
                }
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Pm Jobs',
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
    if (this.selectedPerformanceMetricGroup) {
      this.newPmjobs.criteria.performanceMetricGroup.push(this.selectedPerformanceMetricGroup);
    }

    this.isSaving = true;

    this.pmJobService.create(this.newPmjobs).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({ severity: 'success', summary: 'Pm Job', detail: 'Create successfully!', life: 10000 });
          this.isDisplay = false;
          this.isSaving = false;
        } else {
          this.isSaving = false;
          this.messageService.add({ severity: 'error', summary: 'Pm Job', detail: res.body.message, life: 10000 });
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({ severity: 'error', summary: 'Pm Job', detail: res.error.status + ' ' + res.error.message, life: 10000 });
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

  closeCalendar() {
    if (this.newPmjobs.criteria.reportingBoundary) {
      this.isFutureDate = new Date(this.newPmjobs.criteria.reportingBoundary).getTime() > new Date().getTime();
    }
  }
}
