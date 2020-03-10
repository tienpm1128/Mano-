import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { IServerGroup, NewServergroup } from 'app/shared/model/servergroup.model';
import { ServerGroupService } from './server-group.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AccountService } from 'app/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-server-group',
  templateUrl: './server-group.component.html',
  styleUrls: ['./server-group.component.scss']
})
export class ServerGroupComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  newServerGroup = new NewServergroup();
  data: IServerGroup[];
  vimId: string;
  first = 0;
  lastRowsPerPage = 10;
  isDisplayCreate = false;
  isAdmin = false;
  isCheckAll = false;

  selecteds = [];

  isSaving = false;

  constructor(
    private serverGroupService: ServerGroupService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
  }

  ngOnInit() {
    this.newServerGroup.serverGroupRule = null;
    this.loadData();
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
  }

  loadData() {
    this.serverGroupService.getAll(this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
          const selectedTemp = [];
          this.selecteds.forEach((selected: IServerGroup) => {
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
            summary: 'Server Group',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Server Group',
          detail: res.error.status + ' ' + res.error.error,
          life: 10000
        });
      }
    );
  }

  onCreateBtnClicked() {
    this.newServerGroup = new NewServergroup();
    this.isDisplayCreate = true;
  }

  save() {
    this.isSaving = true;
    this.serverGroupService.create(this.newServerGroup, this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.loadData();
          this.messageService.add({
            severity: 'success',
            summary: 'Server Group',
            detail: 'Create successfully',
            life: 10000
          });
          this.isDisplayCreate = false;
          this.isSaving = false;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Server Group',
            detail: res.body.message,
            life: 10000
          });
          this.isSaving = false;
        }
        this.isSaving = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Server Group',
          detail: res.body.message,
          life: 10000
        });
        this.isSaving = false;
      }
    );
  }

  confirmDelete(serverGroupId?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        if (serverGroupId) {
          this.serverGroupService.delete(serverGroupId, this.vimId).subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.messageService.add({ severity: 'success', summary: 'Server Group', detail: 'Delete successfully!', life: 10000 });
                this.selecteds = this.selecteds.filter((item: IServerGroup) => {
                  return item.id !== serverGroupId;
                });
                this.loadData();
              } else {
                this.messageService.add({ severity: 'error', summary: 'Server Group', detail: res.body.message, life: 10000 });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Server Group',
                detail: res.error.status + ' ' + res.error.error,
                life: 10000
              });
            }
          );
        } else {
          let count = 0;
          this.selecteds.forEach((servergroup: IServerGroup) => {
            this.serverGroupService.delete(servergroup.id, this.vimId).subscribe(
              res => {
                if (res.body.errorCode === '00') {
                  count++;
                  if (count === this.selecteds.length) {
                    this.messageService.add({ severity: 'success', summary: 'Server Group', detail: 'Delete successfully!', life: 10000 });
                    this.selecteds = [];
                    this.loadData();
                  }
                } else {
                  this.messageService.add({ severity: 'error', summary: 'Server Group', detail: res.body.message, life: 10000 });
                }
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Server Group',
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

  objToString(obj: object) {
    if (obj) {
      return Object.entries(obj);
    }
    return [];
  }
}
