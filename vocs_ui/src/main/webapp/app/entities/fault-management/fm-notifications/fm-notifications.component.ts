import { Component, OnInit, ViewChild } from '@angular/core';
import { IfmNotifications } from 'app/shared/model/fmNotifications.model';
import { FmNotificationsService } from './fm-notifications.service';

@Component({
  selector: 'jhi-fm-notifications',
  templateUrl: './fm-notifications.component.html',
  styleUrls: ['./fm-notifications.component.scss']
})
export class FmNotificationsComponent implements OnInit {
  @ViewChild('dt', { static: true }) dt;

  fmNotifications: IfmNotifications[];

  constructor(private fmNotificationsService: FmNotificationsService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.fmNotificationsService.query().subscribe(res => {
      this.fmNotifications = res.body.data;
    });
  }
}
