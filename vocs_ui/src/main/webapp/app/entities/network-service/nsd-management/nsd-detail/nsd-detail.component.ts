import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { INsd } from 'app/shared/model/nsd.model';
import { IVnfd } from 'app/shared/model/vnfd.model';
import { ConfirmationService, MessageService } from 'primeng/api';
import { NsdManagementService } from 'app/entities/network-service/nsd-management/nsd-management.service';

@Component({
  selector: 'jhi-nsd-detail',
  templateUrl: './nsd-detail.component.html',
  styleUrls: ['./nsd-detail.component.scss']
})
export class NsdDetailComponent implements OnInit {
  nsdDetail: INsd;
  vnfds: IVnfd[];
  vnfdDetail: IVnfd;
  displayDetailVnfd = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private confirmationService: ConfirmationService,
    private nsdManagementService: NsdManagementService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nsdDetail }) => {
      this.nsdDetail = nsdDetail;
      this.nsdManagementService.findVnfds(nsdDetail.nsdId).subscribe(
        res => {
          if (res.body.errorCode === '00') {
            this.vnfds = res.body.data;
          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'VNFD',
              detail: res.body.message,
              life: 10000
            });
          }
        },
        vnfds => {
          this.messageService.add({
            severity: 'error',
            summary: 'VNFD',
            detail: vnfds.error.status + ' ' + vnfds.error.message,
            life: 10000
          });
        }
      );
    });
  }

  loadVnfds() {
    this.activatedRoute.data.subscribe(({ vnfds }) => {
      this.vnfds = vnfds;
    });
  }

  showVnfdDetail(vnfdDetail) {
    this.vnfdDetail = vnfdDetail;
    this.displayDetailVnfd = true;
  }

  confirmDelete(id?: string) {
    this.confirmationService.confirm({
      header: 'Confirm Delete',
      accept: () => {
        this.nsdManagementService.delete(id).subscribe(
          res => {
            if (res.body.errorCode === '00') {
              this.messageService.add({ severity: 'success', summary: 'VNFD', detail: 'Delete successfully!', life: 10000 });
              this.nsdManagementService.findVnfds(this.nsdDetail.nsdId).subscribe(
                vnfds => {
                  if (res.body.errorCode === '00') {
                    this.vnfds = vnfds.body.data;
                  } else {
                    this.messageService.add({
                      severity: 'error',
                      summary: 'VNFD',
                      detail: vnfds.body.message,
                      life: 10000
                    });
                  }
                },
                vnfds => {
                  this.messageService.add({
                    severity: 'error',
                    summary: 'VNFD',
                    detail: vnfds.error.status + ' ' + vnfds.error.message,
                    life: 10000
                  });
                }
              );
            }
          },
          res => {
            this.messageService.add({
              severity: 'error',
              summary: 'VNFD',
              detail: res.error.status + ' ' + res.error.message,
              life: 10000
            });
          }
        );
      }
    });
  }
}
