import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Threshold } from 'app/shared/model/threshold.model';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'jhi-detail-thresholds',
  templateUrl: './detail-thresholds.component.html',
  styleUrls: ['./detail-thresholds.component.scss']
})
export class DetailThresholdsComponent implements OnInit {
  thresholdsDetail: Threshold;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ thresholdsDetail }) => {
      this.thresholdsDetail = thresholdsDetail;
    });
  }
}
