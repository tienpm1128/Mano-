import { Component, OnInit } from '@angular/core';
import { IManoService } from 'app/shared/model/manoService.model';
import { ManoServiceService } from 'app/entities/service/mano-service/mano-service.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'jhi-mano-service',
  templateUrl: './mano-service.component.html',
  styleUrls: ['./mano-service.component.scss']
})
export class ManoServiceComponent implements OnInit {
  manoServices: IManoService[];

  constructor(private manoService: ManoServiceService, private messageService: MessageService) {}

  ngOnInit() {
    this.manoService.getManoServices().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          if (res.body.data[0].supportingServices) {
            this.manoServices = res.body.data[0].supportingServices;
          }
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Mano Service',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Mano Service',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }
}
