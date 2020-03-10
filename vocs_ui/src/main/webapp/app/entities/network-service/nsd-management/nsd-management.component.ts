import { ChangeDetectorRef, Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { INsd, Nsd } from 'app/shared/model/nsd.model';
import { NsdManagementService } from 'app/entities/network-service/nsd-management/nsd-management.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AccountService } from 'app/core';
import { saveAs } from 'file-saver';

@Component({
  selector: 'jhi-nsd-management',
  templateUrl: './nsd-management.component.html',
  styleUrls: ['./nsd-management.component.scss']
})
export class NsdManagementComponent implements OnInit {
  @ViewChild('fileUpload', { static: true }) uploadForm: any;
  @ViewChild('dt', { static: true }) dt;
  @ViewChildren('selectAll') selectAll;

  isCheckAll = false;
  selecteds = [];
  editingNSD = new Nsd();
  uploadFiles: any;
  displayUploader = false;
  isDisplayEditor = false;
  data: INsd[];
  isAdmin = false;
  hasErrorFileType = false;
  isDisplayNotification = false;
  uploadFileName = '';
  dem = 0;
  first = 0;
  lastRowsPerPage = 10;

  constructor(
    private nsdManagementService: NsdManagementService,
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
    this.nsdManagementService.query().subscribe(res => {
      if (res.body.errorCode === '00') {
        this.data = res.body.data;
        const selectedTemp = [];
        this.selecteds.forEach((selected: INsd) => {
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

  updateNsd() {
    this.nsdManagementService.update(this.editingNSD).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          if (this.editingNSD.nsdOperationalStateType !== 'DISABLED') {
            this.selecteds = this.selecteds.filter(nsd => {
              return nsd.id !== this.editingNSD.id;
            });
          }
          this.loadData();
          this.messageService.add({ severity: 'success', summary: 'NSD', detail: 'Update successfully!', life: 10000 });
          this.isDisplayEditor = false;
        } else {
          this.messageService.add({ severity: 'error', summary: 'NSD', detail: res.body.message, life: 10000 });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'NSD',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  displayEditor(nsd: INsd) {
    this.editingNSD = Object.assign({}, nsd);
    this.isDisplayEditor = true;
  }

  hideDialog() {
    this.editingNSD = new Nsd();
  }

  uploader(event) {
    this.isDisplayNotification = true;
    const file = event.files[0];
    this.uploadFileName = file.name;
    if (this.uploadFileName.includes('.csar') || this.uploadFileName.includes('.CSAR')) {
      this.uploadFiles = file;
      this.hasErrorFileType = false;
    } else {
      this.uploadFiles = null;
      this.hasErrorFileType = true;
    }
  }

  removeFile() {
    this.hasErrorFileType = false;
    this.isDisplayNotification = false;
    this.uploadFiles = null;
  }

  onHide() {
    this.uploadFiles = null;
    this.uploadForm.clear();
    this.hasErrorFileType = false;
    this.isDisplayNotification = false;
    this.displayUploader = false;
  }

  startUpload() {
    // close Upload dialog
    this.displayUploader = false;

    // Ready to upload data
    const formData = new FormData();
    formData.append('filePart', this.uploadFiles);

    this.nsdManagementService.create().subscribe(
      createRes => {
        if (createRes.body.errorCode === '00') {
          this.nsdManagementService.upload(formData, createRes.body.data.id).subscribe(
            response => {
              if (response.body.errorCode === '00') {
                createRes.body.data.nsdOperationalStateType = 'ENABLED';
                this.nsdManagementService.update(createRes.body.data).subscribe(
                  updated => {
                    if (updated.body.errorCode === '00') {
                      this.loadData();
                      this.messageService.add({ severity: 'success', summary: 'NSD', detail: 'Upload successfully!', life: 10000 });
                      this.uploadForm.clear();
                      formData.delete('filePart');
                    } else {
                      this.messageService.add({ severity: 'error', summary: 'NSD', detail: updated.body.message, life: 10000 });
                    }
                  },
                  err => {
                    this.messageService.add({
                      severity: 'error',
                      summary: 'NSD',
                      detail: err.error.status + ' ' + err.error.message,
                      life: 10000
                    });
                  }
                );
              } else {
                this.messageService.add({ severity: 'error', summary: 'NSD', detail: response.body.message, life: 10000 });
              }
            },
            response => {
              this.messageService.add({
                severity: 'error',
                summary: 'NSD',
                detail: response.error.status + ' ' + response.error.message,
                life: 10000
              });
            }
          );
        }
      },
      err => {
        this.messageService.add({
          severity: 'error',
          summary: 'NSD',
          detail: err.error.status + ' ' + err.error.message,
          life: 10000
        });
      }
    );
  }

  confirmDownload(nsd: Nsd) {
    this.confirmationService.confirm({
      header: 'Confirm Download',
      accept: () => {
        if (nsd.nsdOnboardingStateType !== 'ONBOARDED') {
          this.messageService.add({
            severity: 'error',
            summary: 'NSD',
            detail: 'This item is not in the ONBOARDED state.',
            life: 10000
          });
          return;
        }

        this.nsdManagementService.download(nsd.id).subscribe(
          res => {
            const blob = new Blob([res]);
            const file = new File([blob], nsd.nsdName + '.csar', { type: 'text' });
            saveAs(file);
          },
          res => {
            this.messageService.add({
              severity: 'error',
              summary: 'NSD',
              detail: res.error.status + ' ' + res.error.message,
              life: 10000
            });
          }
        );
      }
    });
  }

  confirmDelete(infoId?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (infoId) {
          this.nsdManagementService.delete(infoId).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'NSD', detail: 'Delete successfully!', life: 10000 });
                this.selecteds = this.selecteds.filter((item: INsd) => {
                  return item.id !== infoId;
                });
                this.loadData();
              } else {
                this.messageService.add({ severity: 'error', summary: 'NSD', detail: res.body.message, life: 10000 });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'NSD',
                detail: res.error.status + ' ' + res.error.message,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          this.selecteds.forEach(nsd => {
            this.nsdManagementService.delete(nsd.id).subscribe(res => {
              if (res.body.errorCode === '00') {
                count++;
                if (count === this.selecteds.length) {
                  this.messageService.add({ severity: 'success', summary: 'NSD', detail: 'Delete successfully!', life: 10000 });
                  this.selecteds = [];
                  this.loadData();
                }
              } else {
                this.messageService.add({ severity: 'error', summary: 'NSD', detail: res.body.message, life: 10000 });
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
      let changed = false;
      for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
        if (this.data[i].nsdOperationalStateType !== 'DISABLED' || this.data[i].nsdUsageState !== 'NOT_IN_USE') {
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
          this.selecteds.push(this.data[i]);
        }
        this.selecteds = this.selecteds.filter(nsd => {
          return nsd.nsdOperationalStateType !== 'ENABLED' && nsd.nsdUsageState !== 'IN_USE';
        });
      } else {
        for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
          this.selecteds = this.selecteds.filter(selected => {
            return this.data[i] !== selected;
          });
        }
      }
    } else {
      this.isCheckAll
        ? (this.selecteds = this.dt.filteredValue.filter(
            item => item.nsdOperationalStateType !== 'ENABLED' && item.nsdUsageState !== 'IN_USE'
          ))
        : (this.selecteds = []);
    }
  }

  onSortOrPage() {
    if (this.dt.filteredValue && this.dt.filteredValue.length === 0) {
      this.isCheckAll = false;
    } else {
      const currentItem = this.dt.first + 1 + this.dt._rows > this.dt.totalRecords ? this.dt.totalRecords - this.dt.first : this.dt._rows;
      const notCheckables = this.data
        .slice(this.dt.first, currentItem + this.dt.first)
        .filter(notCheck => notCheck.nsdOperationalStateType !== 'DISABLED' || notCheck.nsdUsageState !== 'NOT_IN_USE').length;
      this.isCheckAll = true;

      for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
        if (
          (!this.selecteds.includes(this.data[i]) &&
            !(this.data[i].nsdOperationalStateType !== 'DISABLED' || this.data[i].nsdUsageState !== 'NOT_IN_USE')) ||
          notCheckables === currentItem
        ) {
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
