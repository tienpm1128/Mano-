import { Component, OnInit, ViewChild } from '@angular/core';
import { ISupportingService } from 'app/shared/model/supporting.service.model';
import { SupportingServiceService } from './supporting-service.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'jhi-supporting-service',
  templateUrl: './supporting-service.component.html',
  styleUrls: ['./supporting-service.component.scss']
})
export class SupportingServiceComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  supportingServices: ISupportingService[];

  constructor(private supportingServiceService: SupportingServiceService, private messageService: MessageService) {}

  ngOnInit() {
    this.supportingServiceService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          if (res.body.data[0].supportingServices) {
            this.supportingServices = res.body.data[0].supportingServices;
          }
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Supporting Service',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Supporting Service',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }
}
