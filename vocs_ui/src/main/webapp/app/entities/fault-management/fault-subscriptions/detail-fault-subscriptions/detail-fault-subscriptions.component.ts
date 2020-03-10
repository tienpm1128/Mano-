import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IFaultSubscription } from 'app/shared/model/faultSubscriptions.model';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'jhi-detail-fault-subscriptions',
  templateUrl: './detail-fault-subscriptions.component.html',
  styleUrls: ['./detail-fault-subscriptions.component.scss']
})
export class DetailFaultSubscriptionsComponent implements OnInit {
  faultSubscriptionsDetail: IFaultSubscription;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ faultSubscriptionsDetail }) => {
      this.faultSubscriptionsDetail = faultSubscriptionsDetail;
    });
  }
}
