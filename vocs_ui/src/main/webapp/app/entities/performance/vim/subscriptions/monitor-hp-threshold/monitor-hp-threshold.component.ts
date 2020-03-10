import { Component, OnInit, ViewChild } from '@angular/core';
import { INsSubscription } from 'app/shared/model/nsSubscription.model';
import { IVnfInstance } from 'app/shared/model/vnfInstance.model';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { JhiEventManager } from 'ng-jhipster';
import { UIChart } from 'primeng/chart';
import { HpPmSubscriptionsService } from 'app/entities/performance/vim/subscriptions/hp-pm-subscriptions.service';

const randomColor = require('randomcolor');

@Component({
  selector: 'jhi-monitor-ns-threshold',
  templateUrl: './monitor-hp-threshold.component.html',
  styleUrls: ['./monitor-hp-threshold.component.scss']
})
export class MonitorHpThresholdComponent implements OnInit {
  @ViewChild('chart', { static: true }) chart: UIChart;

  hppmSubscription: INsSubscription;
  vnfInstances: IVnfInstance[];
  selectedVnfInstances: IVnfInstance[];

  chartType = 'line';

  thresholdChart = {
    labels: [],
    datasets: []
  };

  constructor(
    private activatedRoute: ActivatedRoute,
    private subscriptionsService: HpPmSubscriptionsService,
    private messageService: MessageService,
    private eventManager: JhiEventManager
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(
      ({ hppmSubscription }) => {
        this.hppmSubscription = hppmSubscription;
        this.subscriptionsService
          .getDetailThreshold(this.hppmSubscription.filter['nsInstanceSubscriptionFilter'].nsInstanceIds[0])
          .subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.hppmSubscription['thresholdDetail'] = res.body.data;
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Threshold',
                  detail: res.body.message,
                  life: 10000
                });
              }
            },
            res => {
              this.messageService.add({
                severity: 'error',
                summary: 'Threshold',
                detail: res.error.status + ' ' + res.error.message,
                life: 10000
              });
            }
          );
      },
      hppmSubscription => {
        this.messageService.add({
          severity: 'error',
          summary: 'HP PM Subscription',
          detail: hppmSubscription.error.status + ' ' + hppmSubscription.error.message,
          life: 10000
        });
      }
    );
    this.drawChart();
  }

  drawChart() {
    this.eventManager.subscribe('notifications', res => {
      const notificationData = res.content;
      if (notificationData.notificationType === 'ThresholdCrossedNotification') {
        this.thresholdChart.datasets = [];
        this.thresholdChart.labels.forEach(vnf => {
          if (vnf.id === notificationData.objectInstanceId) {
            const timeStamp = new Date(parseInt(notificationData.timeStamp, 10))
              .toISOString()
              .replace('T', ' ')
              .replace('Z', '');
            if (this.thresholdChart.labels.length > 0) {
              const index = this.thresholdChart.labels.indexOf(timeStamp);
              if (index > -1) {
                vnf['chartData'].data[index] = notificationData.performanceValue;
              } else {
                this.thresholdChart.labels.push(timeStamp);
                vnf['chartData'].data[vnf['chartData'].data.length] = notificationData.performanceValue;
              }
            } else {
              this.thresholdChart.labels.push(timeStamp);
              vnf['chartData'].data.push(notificationData.performanceValue);
            }
          }
          if (vnf['chartData'].data.length > 0) {
            this.thresholdChart.datasets.push(vnf['chartData']);
          }
          this.chart.refresh();
        });
      }
    });
  }

  changeChartType() {
    switch (this.chartType) {
      case 'bar':
        this.vnfInstances.forEach(vnf => {
          vnf['chartData'].backgroundColor = vnf['chartData'].borderColor;
        });
        break;
      case 'line':
        this.vnfInstances.forEach(vnf => {
          vnf['chartData'].backgroundColor = 'transparent';
        });
        break;
      default:
        break;
    }
    this.chart.reinit();
  }
}
