import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { ImageModel, IImage } from 'app/shared/model/image.model';
import { ImageService } from './image.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AccountService } from 'app/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CommonUntil } from 'app/shared/util/commonUtil';

const TrieSearch = require('trie-search');

@Component({
  selector: 'jhi-image',
  templateUrl: './image.component.html',
  styleUrls: ['./image.component.scss']
})
export class ImageComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  commonUntil = new CommonUntil();

  selecteds = [];
  isCheckAll = false;
  newImage = new ImageModel();
  data: IImage[];
  commonUtil = new CommonUntil();
  first = 0;
  lastRowsPerPage = 10;

  isDisplayCreate = false;
  isAdmin = false;
  vimId: string;
  hasErrorFileSize = false;
  uploadFile: any;
  filePath = '';
  chooseNameFile = '';
  diskFormats: string[] = ['RAW', 'VHD', 'VMDK', 'VHDX', 'VDI', 'ISO', 'QCOW2', 'AKI', 'ARI', 'AMI', 'UNRECOGNIZED'];
  containerFormats: string[] = ['BARE', 'OVF', 'AKI', 'ARI', 'AMI', 'OVA', 'DOCKER', 'UNRECOGNIZED'];
  isSearchCase = false;
  invalidCharacters = ['-', '+', 'e', 'd'];
  isSaving = false;

  constructor(
    private messageService: MessageService,
    private imageService: ImageService,
    private confirmationService: ConfirmationService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
  }

  ngOnInit() {
    this.loadData();
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
  }

  loadData() {
    this.imageService.query(this.vimId).subscribe(res => {
      if (res.body.errorCode === '00') {
        this.data = res.body.data;
        const selectedTemp = [];
        this.selecteds.forEach((selected: IImage) => {
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

  confirmDelete(imageId?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (imageId) {
          this.imageService.delete(imageId, this.vimId).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({
                  severity: 'success',
                  summary: 'Image',
                  detail: 'Delete successfully!',
                  life: 10000
                });
                this.selecteds = this.selecteds.filter((item: IImage) => {
                  return item.id !== imageId;
                });
                this.loadData();
              } else {
                this.messageService.add({ severity: 'error', summary: 'Image', detail: res.body.message, life: 10000 });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Image',
                detail: res.error.status + ' ' + res.error.message,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          this.selecteds.forEach(nsd => {
            this.imageService.delete(nsd.id, this.vimId).subscribe(res => {
              if (res.body.errorCode === '00') {
                count++;
                if (count === this.selecteds.length) {
                  this.messageService.add({
                    severity: 'success',
                    summary: 'Image',
                    detail: 'Delete successfully!',
                    life: 10000
                  });
                  this.loadData();
                  this.selecteds = [];
                }
              } else {
                this.messageService.add({ severity: 'error', summary: 'Image', detail: res.body.message, life: 10000 });
              }
            });
          });
        }
      },
      reject: () => {}
    });
  }

  reset() {
    this.newImage = new ImageModel();
    this.filePath = null;
    this.chooseNameFile = null;
    this.hasErrorFileSize = false;
  }

  checkNumber(min: number, max: number, value: number) {
    return this.commonUtil.checkNumber(min, max, value);
  }

  /*save() {
    this.isSaving = true;
    this.imageService.create(this.newImage, this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({ severity: 'success', summary: 'Image', detail: 'Create successfully!', life: 10000 });
          this.isDisplayCreate = false;
          this.isSaving = false;
        } else {
          this.messageService.add({ severity: 'error', summary: 'Image', detail: res.body.message, life: 10000 });
          this.isSaving = false;
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({ severity: 'error', summary: 'Image', detail: res.error.status + ' ' + res.error.message, life: 10000 });
        this.isSaving = false;
      }
    );
  }*/

  onSelectFile(event) {
    const maxFileSize = 200000000;
    if (event.target.files.length > 0) {
      this.uploadFile = event.target.files[0];
      this.filePath = this.commonUntil.removeAccents(this.uploadFile.name);
      if (this.uploadFile.size > maxFileSize) {
        this.hasErrorFileSize = true;
        this.chooseNameFile = null;
      } else {
        this.hasErrorFileSize = false;
      }
    }
  }

  showImageDialog(imageForm: NgForm, chooseFile: any) {
    imageForm.reset();
    chooseFile.value = null;
    this.hasErrorFileSize = false;
  }

  save() {
    this.isSaving = true;
    this.imageService.create(this.newImage, this.filePath, this.vimId).subscribe(
      res => {
        if (res.errorCode === '00') {
          this.loadData();
          this.messageService.add({ severity: 'success', summary: 'Image', detail: 'Create successfully!', life: 10000 });
          this.isDisplayCreate = false;
        } else {
          this.messageService.add({ severity: 'error', summary: 'Image', detail: res.message, life: 10000 });
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Image',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
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
}
