import { Component, OnInit } from '@angular/core';
import { SoftwareModel, ISoftWare } from 'app/shared/model/software.model';
import { SoftwareService } from './software.service';
import { MessageService } from 'primeng/api';
import { NgForm } from '@angular/forms';
import { AccountService } from 'app/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-software',
  templateUrl: './software.component.html',
  styleUrls: ['./software.component.scss']
})
export class SoftwareComponent implements OnInit {
  newSoftWare = new SoftwareModel();
  softWareDetail = new SoftwareModel();
  softWares: ISoftWare[];
  isCheckAll = false;
  isDisplayCreate = false;
  isDisplayDetail = false;
  chooseNameFile = '';
  uploadFile: any;
  isAdmin: boolean;
  currentLoggedVimUser: any;
  isCreateBtnHidden = true;
  hasErrorFileSize = false;
  currentIndexCmdExecutor = -1;
  isResetForm = false;
  vimId: string;
  isSaving = false;
  isInvalidCommandExe = false;

  constructor(
    private messageService: MessageService,
    private softWareService: SoftwareService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute
  ) {
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
  }

  ngOnInit() {
    this.loadSoftWare();

    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.currentLoggedVimUser = this.accountService.getCurrentLoggedVimUser(this.vimId);
    if (this.isAdmin && this.currentLoggedVimUser) {
      this.isCreateBtnHidden = false;
    }
  }

  loadSoftWare() {
    this.isCheckAll = false;
    this.softWareService.query(this.vimId).subscribe(res => {
      if (res.body.errorCode === '00') {
        this.softWares = res.body.data;
      }
    });
  }

  save() {
    this.isSaving = true;
    this.softWareService.create(this.newSoftWare, this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadSoftWare();
          this.messageService.add({ severity: 'success', summary: 'SoftWare', detail: 'Create successfully!', life: 10000 });
          this.isDisplayCreate = false;
          this.isSaving = false;
        } else {
          this.messageService.add({ severity: 'error', summary: 'SoftWare', detail: res.body.message, life: 10000 });
          this.isSaving = false;
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'SoftWare',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
        this.isSaving = false;
      }
    );
  }

  showSoftwareDetail(id: string) {
    this.softWareService.find(id).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.softWareDetail = res.body.data;
          this.isDisplayDetail = true;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Software',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Software',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  onFileSelect(event) {
    const maxFileSize = 200000000;
    if (event.target.files.length > 0) {
      this.uploadFile = event.target.files[0];
      this.newSoftWare.fileName = this.uploadFile.name;

      if (this.uploadFile.size > maxFileSize) {
        this.hasErrorFileSize = true;
        // this.newSoftWare.fileName = null;
        this.chooseNameFile = null;
      } else {
        this.hasErrorFileSize = false;
      }
    }
  }

  addCommandExecutor() {
    if (typeof this.newSoftWare.cmdExecutors === 'undefined' || this.newSoftWare.cmdExecutors === null) {
      this.newSoftWare.cmdExecutors = [];
    }

    if (this.currentIndexCmdExecutor !== -1) {
      if (this.newSoftWare.cmdExecutors[this.currentIndexCmdExecutor] !== '' && !this.newSoftWare.cmdExecutors.includes('')) {
        this.newSoftWare.cmdExecutors.unshift('');
        ++this.currentIndexCmdExecutor;
      }
    } else if (!this.newSoftWare.cmdExecutors.includes('')) {
      this.newSoftWare.cmdExecutors.unshift('');
      ++this.currentIndexCmdExecutor;
    }
    this.isInvalidCommandExe = true;
  }

  removeCommandExecutor(index) {
    this.newSoftWare.cmdExecutors.splice(index, 1);
    --this.currentIndexCmdExecutor;

    if (this.newSoftWare.cmdExecutors) {
      this.isInvalidCommandExe = this.newSoftWare.cmdExecutors.includes('');
    }
  }

  trackByFn(index: any, item: any) {
    return index;
  }

  resetForm() {
    this.newSoftWare.fileName = '';
    this.newSoftWare.version = null;
    this.newSoftWare.installedFolderPath = null;
    this.newSoftWare.cmdExecutors = null;

    this.chooseNameFile = null;
    this.currentIndexCmdExecutor = -1;
    this.isResetForm = true;
    this.hasErrorFileSize = false;
    this.uploadFile = null;
  }

  showNewSoftwareDlg(softwareForm: NgForm, uploadFile: HTMLInputElement) {
    softwareForm.reset();
    uploadFile.value = null;

    this.newSoftWare.cmdExecutors = null;
    this.currentIndexCmdExecutor = -1;
    this.isResetForm = false;
    this.hasErrorFileSize = false;
  }

  submit() {
    if (!(this.newSoftWare.fileName && this.newSoftWare.version && this.newSoftWare.installedFolderPath)) {
      return;
    }

    // upload file - post
    const formData = new FormData();
    formData.append('filePart', this.uploadFile);
    let openStackUserId = '';
    let projectId = '';

    if (this.currentLoggedVimUser !== null) {
      openStackUserId = this.currentLoggedVimUser.userId;
      projectId = this.currentLoggedVimUser.projectId;
    } else {
      return;
    }

    this.softWareService.upload(formData, openStackUserId, projectId, this.vimId).subscribe(
      res => {
        if (res.errorCode === '00') {
          // upload infor - put
          this.newSoftWare.id = res.data;
          if (this.currentIndexCmdExecutor !== -1) {
            if (this.newSoftWare.cmdExecutors[this.currentIndexCmdExecutor] === '') {
              this.newSoftWare.cmdExecutors.splice(this.currentIndexCmdExecutor, 1);
            }
          }

          this.softWareService.update(this.newSoftWare, this.vimId).subscribe(
            resUpdate => {
              if (resUpdate.body.errorCode === '00') {
                formData.delete('file');
                this.loadSoftWare();

                this.isDisplayCreate = false;
                this.messageService.add({ severity: 'success', summary: 'Software', detail: 'Create successfully!', life: 10000 });
              }
            },
            err => {
              this.messageService.add({
                severity: 'error',
                summary: 'Software',
                detail: err.error.status + ' ' + err.error.message,
                life: 10000
              });
            }
          );
        } else {
          this.messageService.add({ severity: 'error', summary: 'Software', detail: res.errorCode + ' ' + res.message, life: 10000 });
        }
      },
      response => {
        this.messageService.add({
          severity: 'error',
          summary: 'Software',
          detail: response.error.status + ' ' + response.error.message,
          life: 10000
        });
      }
    );
  }

  onCommandExeChange(value: any, i: number) {
    if (value) {
      this.newSoftWare.cmdExecutors[i] = value.trim();
    }
    this.isInvalidCommandExe = value === '' || this.newSoftWare.cmdExecutors.includes('');
  }
}
