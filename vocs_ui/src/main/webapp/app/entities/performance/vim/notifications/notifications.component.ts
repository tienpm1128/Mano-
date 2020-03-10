import { Component, OnInit, ViewChild } from '@angular/core';
import { INsNotifications } from 'app/shared/model/ns.notifications.model';
import { NotificationsService } from './notifications.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'jhi-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class VimNotificationsComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  notifications: INsNotifications[];

  isCheckAll = false;
  isDisplay = false;
  isAdmin = false;
  isSaving = false;

  constructor(private notificationsService: NotificationsService, private messageService: MessageService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.notificationsService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.notifications = res.body.data;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Notification',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Notification',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }
}
