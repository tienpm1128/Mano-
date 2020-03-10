import { Component, OnInit } from '@angular/core';
import { IAlarm } from 'app/shared/model/alarm.model';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AlarmService } from 'app/entities/fault-management/alarm/alarm.service';
import { AccountService } from 'app/core';

@Component({
  selector: 'jhi-alarm-detail',
  templateUrl: './alarm-detail.component.html',
  styleUrls: ['./alarm-detail.component.scss']
})
export class AlarmDetailComponent implements OnInit {
  alarmDetail: IAlarm;
  isAdmin = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private confirmationService: ConfirmationService,
    private alarmService: AlarmService,
    private messageService: MessageService,
    private accountService: AccountService
  ) {}

  ngOnInit() {
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.activatedRoute.data.subscribe(({ alarmDetail }) => {
      this.alarmDetail = alarmDetail;
    });
  }

  confirmEdit(idAlarm: string) {
    this.confirmationService.confirm({
      header: 'Acknowledgement Alarm Confirm',
      accept: () => {
        this.alarmService.update(idAlarm, { ackState: 'ACKNOWLEDGED' }).subscribe(
          res => {
            if (res.body.errorCode === '00') {
              this.messageService.add({ severity: 'success', summary: 'Alarm', detail: 'Update successfully!', life: 10000 });
              this.alarmDetail.ackState = res.body.data.ackState;
            } else {
              this.messageService.add({ severity: 'error', summary: 'Alarm', detail: res.body.message, life: 10000 });
            }
          },
          res => {
            this.messageService.add({ severity: 'error', summary: 'Alarm', detail: res.error.status + ' ' + res.error.error, life: 10000 });
          }
        );
      }
    });
  }

  getColor(eventType: string) {
    switch (eventType) {
      case 'CRITICAL':
        return 'red';
      case 'WARNING':
        return '#f57568';
      case 'MAJOR':
        return '#e85914';
      case 'MINOR':
        return '#d88a49';
      case 'INDETERMINATE':
        return 'rgb(232, 199, 93)';
      default:
        return '#111';
    }
  }
}
