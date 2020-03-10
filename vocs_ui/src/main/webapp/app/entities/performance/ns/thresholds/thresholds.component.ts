import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { IThreshold, Threshold } from 'app/shared/model/threshold.model';
import { ThresholdsService } from './thresholds.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AccountService } from 'app/core';
import { INsInstance } from 'app/shared/model/nsInstance.model';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';
import { CommonUntil } from 'app/shared/util/commonUtil';

@Component({
  selector: 'jhi-thresholds',
  templateUrl: './thresholds.component.html',
  styleUrls: ['./thresholds.component.scss']
})
export class ThresholdsComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  selecteds = [];
  isCheckAll = false;

  data: IThreshold[];
 
  newThresholds: IThreshold;
  nsInstances: INsInstance[];

  commonUtil = new CommonUntil();
  commaRegex: RegExp = /,/g;
  blockSpace: RegExp = /[^\s]/;
  first = 0;
  lastRowsPerPage = 10;

  isDisplay = false;
  isAdmin = false;
  isSaving = false;

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

  constructor(
    private thresholdsService: ThresholdsService,
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
    this.newThresholds = new Threshold();

    this.newThresholds.criteria = {
      performanceMetric: null,
      thresholdType: null
    };

    this.newThresholds.criteria.simpleThresholdDetails = {
      thresholdValue: null,
      hysteresis: null
    };

    this.newThresholds.objectInstanceId = null;
  }

  loadData() {
    this.thresholdsService.query().subscribe(res => {
      this.data = res.body.data;
      console.log('test',this.data);
      const selectedTemp = [];
      this.selecteds.forEach((selected: IThreshold) => {
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

  loadNsInstance() {
    this.nsInstanceService.query().subscribe(res => {
      this.nsInstances = res.body.data;
    });
  }

  checkNumber(min: number, max: number, value: number) {
    this.isSaving = !!this.commonUtil.checkNumber(min, max, value);
    if (value !== undefined) {
      return this.commonUtil.checkNumber(min, max, value);
    }
  }

  confirmDelete(id?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (id) {
          this.thresholdsService.delete(id).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'Thresholds', detail: 'Delete successfully!', life: 10000 });
                this.selecteds = this.selecteds.filter((item: IThreshold) => {
                  return item.id !== id;
                });
                this.loadData();
              } else {
                this.messageService.add({ severity: 'error', summary: 'Thresholds', detail: res.body.message, life: 10000 });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Thresholds',
                detail: res.error.status + ' ' + res.error.error,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          this.selecteds.forEach((threshold: IThreshold) => {
            this.thresholdsService.delete(threshold.id).subscribe(
              res => {
                if (res.body.errorCode === '00') {
                  count++;
                  if (count === this.selecteds.length) {
                    this.messageService.add({ severity: 'success', summary: 'Thresholds', detail: 'Delete successfully!', life: 10000 });
                    this.selecteds = [];
                    this.loadData();
                  }
                } else {
                  this.messageService.add({ severity: 'error', summary: 'Thresholds', detail: res.body.message, life: 10000 });
                }
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Thresholds',
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

    this.thresholdsService.create(this.newThresholds).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({ severity: 'success', summary: 'Thresholds', detail: 'Create successfully!', life: 10000 });
          this.isDisplay = false;
          this.isSaving = false;
        } else {
          this.isSaving = false;
          this.messageService.add({ severity: 'error', summary: 'Thresholds', detail: res.body.message, life: 10000 });
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Thresholds',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
        this.isSaving = false;
      }
    );
  }

  replaceSpace1() {
    this.newThresholds.criteria.simpleThresholdDetails.thresholdValue = this.newThresholds.criteria.simpleThresholdDetails.thresholdValue.replace(
      /\s/g,
      ''
    );
  }

  replaceSpace2() {
    this.newThresholds.criteria.simpleThresholdDetails.hysteresis = this.newThresholds.criteria.simpleThresholdDetails.hysteresis.replace(
      /\s/g,
      ''
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
