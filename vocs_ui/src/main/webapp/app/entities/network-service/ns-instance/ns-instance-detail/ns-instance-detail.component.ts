import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VimInstanceService } from 'app/entities/resource-management/vim-instance/vim-instance.service';
import { IInstantiateVnfRequest, InstantiateVnfRequest } from 'app/shared/model/instantiateVnfRequest.model';
import { INsInstance } from 'app/shared/model/nsInstance.model';
import { IProjects } from 'app/shared/model/projects.model';
import { IScaleVnfRequest } from 'app/shared/model/scaleVnfRequest.model';
import { ITerminateVnfRequest, TerminateVnfRequest } from 'app/shared/model/terminateVnfRequest.model';
import { IVimInstance, VimInstanceModel } from 'app/shared/model/vimInstance.model';
import { IVnfInstance, VnfInstance } from 'app/shared/model/vnfInstance.model';
import { ConfirmationService, MessageService } from 'primeng/api';
import { NsInstanceService } from '../ns-instance.service';
import { AccessInfo } from 'app/shared/model/accessInfo.model';
import { ScaleVnfRequest } from 'app/shared/model/scaleVnfRequest.model';
import { VimConnectionInfo } from 'app/shared/model/vimConnectionInfo.model';
import { InputUntil } from 'app/shared/util/input-util';
import { ProjectService } from '../../../resource-management/vim-instance/project/project.service';
import { VIM_CONNECTION_INFO_KEY } from 'app/app.constants';
import { AccountService, LoginService } from 'app/core';
import { CommonUntil } from 'app/shared/util/commonUtil';

enum LAYOUTS {
  list = 'List',
  card = 'Card',
  poto = 'Poto',
  chart ='Chart'
};

@Component({
  selector: 'jhi-ns-instance-detail',
  templateUrl: './ns-instance-detail.component.html',
  styleUrls: ['./ns-instance-detail.component.scss']
})
export class NsInstanceDetailComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  layouts = LAYOUTS;
  layout = 'List';
  checkLayout = false;

  isCheckAll = false;
  selecteds = [];
  lastRowsPerPage = 10;
  first = 0;

  active = {fill: '#ed2e4f'};

  nsInstanceDetail: INsInstance;
  vnfIns: IVnfInstance[];
  vnfInsDetail: IVnfInstance;
  isDisplayInstatiate = false;
  isDisplayScale = false;
  isDisplayTerminate = false;
  isDisplayVimInfo = false;
  currentKey: string;
  currentValue: string;
  vnfInsKeyValues: any[];
  instantiateVnfRequest: IInstantiateVnfRequest = new InstantiateVnfRequest();
  scaleVnfRequest: IScaleVnfRequest = new ScaleVnfRequest();
  terminateVnfRequest: ITerminateVnfRequest = new TerminateVnfRequest();
  commonUtil = new CommonUntil();
  inputUntil = new InputUntil();
  vimInfo = new VimConnectionInfo();
  // ============ VARIABLES FOR SET VIM CONNECTION DIALOG ============
  // isCheckAll = false;
  selectedVnfInstances: VnfInstance[];
  vimInstances: VimInstanceModel[];
  selectedVim: IVimInstance;
  userVimProjects: IProjects[];
  accessInfo = new AccessInfo();
  userVimId = '';
  // ============ END VIM CONNECTION DIALOG ============

  blockSpace: RegExp = /[^\s]/;
  test: RegExp = /[a-zA-Z]/;
  password_regex: RegExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#()'"\=\-\\\[\]~^|_/.,:;+])[A-Za-z\d@$!%*?&#()'"\=\-\\\[\]~^|_/.,:;+]{2,25}$/;

  isAdmin = false;
  currentPageReportTemplate = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private nsInstanceService: NsInstanceService,
    private confirmationService: ConfirmationService,
    private vimInstanceService: VimInstanceService,
    private projectService: ProjectService,
    private messageService: MessageService,
    private accountService: AccountService,
    private loginService: LoginService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.accessInfo.PROJECT = null;
    this.activatedRoute.data.subscribe(
      ({ nsInstanceDetail }) => {
        this.nsInstanceDetail = nsInstanceDetail;
        if (nsInstanceDetail.vnfInstances) {
          this.vnfIns = nsInstanceDetail.vnfInstances;
        }
        this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
      },
      nsInstanceDetail => {
        this.messageService.add({
          severity: 'error',
          summary: 'Ns Instance',
          detail: nsInstanceDetail.body.message,
          life: 10000
        });
      }
    );
    this.vimInstanceService.query().subscribe(res => {
      if (res.body.error === false) {
        this.vimInstances = res.body.data;
      }
    });
    // selected default layout 
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

  onClick(str: string) {
    this.layout = str;
    this.checkLayout = true;
  }
  /* checkbox */
  
  check() {
    const currentItem = this.dt.first + 1 + this.dt._rows > this.vnfIns.length ? this.vnfIns.length - this.dt.first : this.dt._rows;
    !this.dt.filteredValue || (this.dt.filteredValue && this.dt.filteredValue.length > 0)
      ? (this.isCheckAll = this.selecteds.length !== 0)
      : (this.isCheckAll = false);
    if (!this.dt.filteredValue) {
      let changed = false;
      for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
        if (this.vnfIns[i].instantiationState === 'INSTANTIATED') {
          continue;
        }
        changed = true;
        if (!this.selecteds.includes(this.vnfIns[i])) {
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
          if (this.vnfIns[i].instantiationState !== 'INSTANTIATED') {
            this.selecteds = [...this.selecteds, this.vnfIns[i]];
          }
        }
      } else {
        for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
          this.selecteds = this.selecteds.filter(selected => {
            return this.vnfIns[i] !== selected;
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
      const notCheckables = this.vnfIns
        .slice(this.dt.first, currentItem + this.dt.first)
        .filter(notCheck => notCheck.instantiationState === 'INSTANTIATED').length;
      this.isCheckAll = true;

      for (let i = this.dt.first; i < currentItem + this.dt.first; i++) {
        if ((!this.selecteds.includes(this.vnfIns[i]) && this.vnfIns[i].instantiationState !== 'INSTANTIATED') || notCheckables === currentItem) {
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

  loadData() {
    this.nsInstanceService.find(this.nsInstanceDetail.id).subscribe(res => {
      if (res.body.errorCode === '00') {
        this.nsInstanceDetail = res.body.data;
        this.vnfIns = this.nsInstanceDetail.vnfInstances;
      }
    });
  }

  onScaleBtnClicked(vnfIns) {
    this.vnfInsDetail = vnfIns;
    this.vnfInsKeyValues = [];
    this.isDisplayScale = true;
    this.scaleVnfRequest = new ScaleVnfRequest();
    this.currentKey = this.currentValue = null;
  }

  onTerminateBtnClicked(vnfIns) {
    this.vnfInsDetail = vnfIns;
    this.vnfInsKeyValues = [];
    this.isDisplayTerminate = true;
    this.terminateVnfRequest = new TerminateVnfRequest();
    this.currentKey = this.currentValue = null;
  }

  checkAlreadySetVimInfo(vnfIns) {
    if (localStorage.getItem(VIM_CONNECTION_INFO_KEY)) {
      const vimConnectionInfo = JSON.parse(localStorage.getItem(VIM_CONNECTION_INFO_KEY));
      if (vnfIns.id in vimConnectionInfo) {
        return true;
      }
    }
    return false;
  }

  onInstantiateBtnClicked(vnfIns: IVnfInstance) {
    if (this.checkAlreadySetVimInfo(vnfIns)) {
      this.vnfInsDetail = vnfIns;
      this.vnfInsKeyValues = [];
      this.isDisplayInstatiate = true;
      this.currentKey = this.currentValue = null;
      this.vimInfo = new VimConnectionInfo();
      const info = JSON.parse(localStorage.getItem(VIM_CONNECTION_INFO_KEY));
      if (info.hasOwnProperty(vnfIns.id)) {
        this.vimInfo = info[vnfIns.id];
      }
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'VNF Instance',
        detail: 'Please set connection info for this VNF Instance!',
        life: 10000
      });
    }
  }

  onDeleteBtnClicked(vnfIns) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        this.nsInstanceService.deleteVnfInstance(vnfIns.id).subscribe(
          res => {
            if (res.body.errorCode === '00') {
              this.messageService.add({ severity: 'success', summary: 'VNF Instance', detail: res.body.message, life: 10000 });
              this.loadData();
            } else {
              this.messageService.add({ severity: 'error', summary: 'VNF Instance', detail: res.body.message, life: 10000 });
            }
          },
          res => {
            this.messageService.add({
              severity: 'error',
              summary: 'VNF Instance',
              detail: res.error.status + ' ' + res.error.message,
              life: 10000
            });
          }
        );
      }
    });
  }

  onSetVimConnection() {
    this.isDisplayVimInfo = true;
    this.selectedVim = null;
    this.userVimProjects = [];
    this.accessInfo.USER_NAME = this.accessInfo.PASSWORD = '';
    this.isCheckAll = false;
    this.selectedVnfInstances = [];
  }

  addKeyValue() {
    if (this.currentKey && this.currentValue) {
      let duplicate = false;
      // check duplicate key
      this.vnfInsKeyValues.forEach(item => {
        if (item.key === this.currentKey) {
          item.value = this.currentValue;
          duplicate = true;
        }
      });
      if (!duplicate) {
        this.vnfInsKeyValues.push({
          key: this.currentKey,
          value: this.currentValue
        });
      }
      this.currentKey = '';
      this.currentValue = '';
    }
  }

  closeDialog() {
    this.isDisplayInstatiate = false;
    this.isDisplayScale = false;
    this.isDisplayVimInfo = false;
    this.isDisplayTerminate = false;
    this.currentKey = null;
    this.currentValue = null;
  }

  checkNumber(min: number, max: number, value: number) {
    if (value !== undefined) {
      return this.commonUtil.checkNumber(min, max, value);
    }
  }

  removeKeyValue(index) {
    this.vnfInsKeyValues.splice(index, 1);
  }

  instantiate() {
    this.instantiateVnfRequest.init(this.vnfInsDetail.instantiatedVnfInfo);
    this.instantiateVnfRequest.additionalParams = {};
    this.vnfInsKeyValues.forEach(item => {
      this.instantiateVnfRequest.additionalParams[item['key']] = item['value'];
    });

    if (localStorage.getItem(VIM_CONNECTION_INFO_KEY)) {
      const vimConnectionInfo = JSON.parse(localStorage.getItem(VIM_CONNECTION_INFO_KEY));
      if (this.vnfInsDetail.id in vimConnectionInfo) {
        this.instantiateVnfRequest.vimConnectionInfo = [];
        this.instantiateVnfRequest.vimConnectionInfo.push(vimConnectionInfo[this.vnfInsDetail.id]);
      }
    }
    this.nsInstanceService.instantiateVnfInstance(this.vnfInsDetail.id, this.instantiateVnfRequest).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.messageService.add({ severity: 'success', summary: 'VNF Instance', detail: res.body.message, life: 10000 });
          this.loadData();
        } else {
          this.messageService.add({ severity: 'error', summary: 'VNF Instance', detail: 'Instantiate VnfInstance failed!', life: 10000 });
        }
        this.isDisplayInstatiate = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'VNF Instance',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  scale() {
    this.scaleVnfRequest.additionalParams = {};
    this.vnfInsKeyValues.forEach(item => {
      this.scaleVnfRequest.additionalParams[item['key']] = item['value'];
    });
    this.nsInstanceService.scale(this.vnfInsDetail.id, this.scaleVnfRequest).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.messageService.add({ severity: 'success', summary: 'VNF Instance', detail: res.body.message, life: 10000 });
          this.loadData();
        } else {
          this.messageService.add({ severity: 'error', summary: 'VNF Instance', detail: res.body.message, life: 10000 });
        }
        this.isDisplayScale = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'VNF Instance',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  terminate() {
    this.terminateVnfRequest.additionalParams = {};
    this.vnfInsKeyValues.forEach(item => {
      this.terminateVnfRequest.additionalParams[item['key']] = item['value'];
    });
    this.nsInstanceService.terminate(this.vnfInsDetail.id, this.terminateVnfRequest).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.messageService.add({ severity: 'success', summary: 'VNF Instance', detail: res.body.message, life: 10000 });
          this.loadData();
        } else {
          this.messageService.add({ severity: 'error', summary: 'VNF Instance', detail: res.body.message, life: 10000 });
        }
        this.isDisplayTerminate = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'VNF Instance',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  /**
   * Saves vim info
   */
  saveVimInfo() {
    const vimConnectionInfo = new VimConnectionInfo();
    vimConnectionInfo.accessInfo = this.accessInfo;
    vimConnectionInfo.vimName = this.selectedVim.name;
    vimConnectionInfo.vimType = this.selectedVim.type;
    vimConnectionInfo.vimId = this.selectedVim.id;
    vimConnectionInfo.accessInfo.USER = this.userVimId;
    /*
      get current vimConnectionInfo from sessionStorage to update
    */
    const storedVimConnectionInfo = localStorage.getItem(VIM_CONNECTION_INFO_KEY)
      ? JSON.parse(localStorage.getItem(VIM_CONNECTION_INFO_KEY))
      : {};
    for (const vnfInstance of this.selectedVnfInstances) {
      storedVimConnectionInfo[vnfInstance.id] = vimConnectionInfo;
    }
    localStorage.setItem(VIM_CONNECTION_INFO_KEY, JSON.stringify(storedVimConnectionInfo));
    this.messageService.add({
      severity: 'success',
      summary: 'Set Vim Connection Information',
      detail: 'Set Vim Connection Information successfully!',
      life: 10000
    });
    this.isDisplayVimInfo = false;
  }

  /**
   * Verifys user
   * * Used to verify credentials of a vim instance
   */
  verifyUser() {
    this.userVimProjects = [];
    this.accessInfo.PROJECT = null;
    // check username and password here
    this.loginService
      .loginVim(
        {
          username: this.accessInfo.USER_NAME,
          password: this.accessInfo.PASSWORD
        },
        this.selectedVim.id,
        this.selectedVim.name,
        false
      )
      .then(res => {
        if (!res['body']) {
          this.userVimProjects = [];
          this.userVimId = res['userId'];
          this.projectService.findProjectsByVimUser(res['userId'], this.selectedVim.id).subscribe(
            projectsRes => {
              if (projectsRes.body.errorCode === '00') {
                this.userVimProjects = projectsRes.body.data;
                this.messageService.add({
                  severity: 'success',
                  summary: 'Verify Vim user',
                  detail: 'Successfully',
                  life: 10000
                });
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Vim project',
                  detail: projectsRes.body.message,
                  life: 10000
                });
              }
            },
            projectsRes => {
              this.messageService.add({
                severity: 'error',
                summary: 'Vim project',
                detail: projectsRes.error.status + ' ' + projectsRes.error.message,
                life: 10000
              });
            }
          );
        } else {
          /*this.messageService.add({
            severity: 'error',
            summary: 'VNF Instance',
            detail: res['body'].message,
            life: 10000
          });*/
          this.userVimProjects = [];
          this.accessInfo.PROJECT = null;
        }
      })
      .catch(res => {
        /*this.messageService.add({
          severity: 'error',
          summary: 'VNF Instance',
          detail: res.body.message,
          life: 10000
        });*/
        this.userVimProjects = [];
        this.accessInfo.PROJECT = null;
      });
  }

  replaceSpace() {
    this.accessInfo.PASSWORD = this.accessInfo.PASSWORD.replace(/\s/g, '');
  }
}
