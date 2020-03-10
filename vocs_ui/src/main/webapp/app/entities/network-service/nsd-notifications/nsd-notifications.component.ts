import { Component, OnInit } from '@angular/core';
import { INsdNotification } from 'app/shared/model/nsdNotification.model';
import { NsdNotificationsService } from 'app/entities/network-service/nsd-notifications/nsd-notifications.service';

@Component({
  selector: 'jhi-nsd-notifications',
  templateUrl: './nsd-notifications.component.html',
  styleUrls: ['./nsd-notifications.component.scss']
})
export class NsdNotificationsComponent implements OnInit {
  nsdNotifications: INsdNotification[];

  constructor(private nsdNotificationsService: NsdNotificationsService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.nsdNotificationsService.query().subscribe(res => {
      if (res.body.errorCode === '00') {
        this.nsdNotifications = res.body.data;
      }
    });
  }
}
