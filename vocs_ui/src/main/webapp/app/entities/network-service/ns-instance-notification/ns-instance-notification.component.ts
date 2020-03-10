import { Component, OnInit, ViewChild } from '@angular/core';
import { INsInstanceNotification, NsInstanceNotification } from 'app/shared/model/nsInstanceNotification.model';
import { NsInstanceNotificationService } from 'app/entities/network-service/ns-instance-notification/ns-instance-notification.service';
import { NsdNotification } from 'app/shared/model/nsdNotification.model';

@Component({
  selector: 'jhi-ns-instance-notification',
  templateUrl: './ns-instance-notification.component.html',
  styleUrls: ['./ns-instance-notification.component.scss']
})
export class NsInstanceNotificationComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  isCheckAll = false;
  nsInstanceNotifications: INsInstanceNotification[];
  dropdown = [{ label: '10', value: 10 }, { label: '20', value: 20 }, { label: '30', value: 30 }];

  constructor(private nsInstanceNotificationService: NsInstanceNotificationService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.isCheckAll = false;
    this.nsInstanceNotificationService.query().subscribe(res => {
      if (res.body.errorCode === '00') {
        this.nsInstanceNotifications = res.body.data;
      }
    });
  }
}
