import { Component, OnInit, ViewChild } from '@angular/core';
import { INsSubscription } from 'app/shared/model/nsSubscription.model';
import { IVnfInstance } from 'app/shared/model/vnfInstance.model';
import { ActivatedRoute } from '@angular/router';
import { NsPmSubscriptionsService } from 'app/entities/performance/ns/subscriptions/ns-pm-subscriptions.service';
import { MessageService } from 'primeng/api';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';
import { JhiEventManager } from 'ng-jhipster';
import { UIChart } from 'primeng/chart';

const randomColor = require('randomcolor');

@Component({
  selector: 'jhi-monitor-ns-threshold',
  templateUrl: './monitor-ns-threshold.component.html',
  styleUrls: ['./monitor-ns-threshold.component.scss']
})
export class MonitorNsThresholdComponent implements OnInit {
  @ViewChild('chart', { static: true }) chart: UIChart;

  nspmSubscription: INsSubscription;
  vnfInstances: IVnfInstance[];
  selectedVnfInstances: IVnfInstance[];

  chartType = 'line';

  thresholdChart = {
    type: 'line',
    labels: [],
    options: {
      legend: {
        align: 'start'
      },
      scales: {
        yAxes: [
          {
            ticks: {
              beginAtZero: true
            },
            scaleLabel: {
              display: true,
              labelString: 'Performance Value'
            }
          }
        ],
        xAxes: [
          {
            offset: true,
            type: 'time',
            time: {
              displayFormats: {
                millisecond: 'HH:mm DD/MM/YYYY',
                second: 'HH:mm DD/MM/YYYY',
                minute: 'HH:mm DD/MM/YYYY',
                hour: 'HH:mm DD/MM/YYYY',
                day: 'HH:mm DD/MM/YYYY',
                week: 'HH:mm DD/MM/YYYY',
                month: 'HH:mm DD/MM/YYYY',
                quarter: 'HH:mm DD/MM/YYYY',
                year: ' HH:mm DD/MM/YYYY'
              }
            },
            ticks: {
              source: 'labels'
            },
            scaleLabel: {
              display: true,
              labelString: 'Time'
            }
          }
        ]
      }
    },
    datasets: []
  };

  constructor(
    private activatedRoute: ActivatedRoute,
    private nsSubscriptionsService: NsPmSubscriptionsService,
    private messageService: MessageService,
    private nsInstanceService: NsInstanceService,
    private eventManager: JhiEventManager
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(
      ({ nspmSubscription }) => {
        this.nspmSubscription = nspmSubscription;
        this.nsInstanceService.find(this.nspmSubscription.filter['nsInstanceSubscriptionFilter'].nsInstanceIds[0]).subscribe(
          res => {
            if (res.body.errorCode === '00') {
              this.vnfInstances = res.body.data.vnfInstances;
              this.vnfInstances.forEach(vnf => {
                const color = randomColor({
                  luminosity: 'light',
                  count: 1,
                  format: 'rgba',
                  alpha: 1
                })[0];
                vnf['chartData'] = {
                  label: vnf.vnfInstanceName,
                  data: [],
                  borderColor: color,
                  backgroundColor: color
                };
              });
            } else {
              this.messageService.add({
                severity: 'error',
                summary: 'Ns Instance',
                detail: res.body.message,
                life: 10000
              });
            }
          },
          res => {
            this.messageService.add({
              severity: 'error',
              summary: 'Ns Instance',
              detail: res.error.status + ' ' + res.error.message,
              life: 10000
            });
          }
        );
        this.nsSubscriptionsService
          .getDetailThreshold(this.nspmSubscription.filter['nsInstanceSubscriptionFilter'].nsInstanceIds[0])
          .subscribe(
            res => {
              if (res.body.errorCode === '00') {
                this.nspmSubscription['thresholdDetail'] = res.body.data;
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
      nspmSubscription => {
        this.messageService.add({
          severity: 'error',
          summary: 'NS PM Subscription',
          detail: nspmSubscription.error.status + ' ' + nspmSubscription.error.message,
          life: 10000
        });
      }
    );
    this.drawChart();
  }

  drawChart() {
    this.eventManager.subscribe('notifications', res => {
      const notificationData = res.content;
      if (notificationData.notificationType === 'ThresholdCrossedNotification' && this.selectedVnfInstances) {
        notificationData.timeStamp = parseInt(notificationData.timeStamp, 10);
        this.thresholdChart.datasets = [];
        this.selectedVnfInstances.forEach(vnf => {
          if (vnf.id === notificationData.objectInstanceId) {
            if (this.thresholdChart.labels.length > 0) {
              const index = this.thresholdChart.labels.indexOf(notificationData.timeStamp);
              if (index > -1) {
                vnf['chartData'].data[index] = notificationData.performanceValue;
                vnf['chartData'].data = [...vnf['chartData'].data];
              } else {
                this.thresholdChart.labels.push(notificationData.timeStamp);
                this.thresholdChart.labels = [...this.thresholdChart.labels];
                vnf['chartData'].data[vnf['chartData'].data.length] = notificationData.performanceValue;
                vnf['chartData'].data = [...vnf['chartData'].data];
              }
            } else {
              this.thresholdChart.labels.push(notificationData.timeStamp);
              vnf['chartData'].data.push(notificationData.performanceValue);
              this.thresholdChart.labels = [...this.thresholdChart.labels];
              vnf['chartData'].data = [...vnf['chartData'].data];
            }
            vnf['chartData']['fill'] = false;
          }
          if (vnf['chartData'].data.length > 0) {
            this.thresholdChart.datasets.push(vnf['chartData']);
            this.thresholdChart.labels = [...this.thresholdChart.labels];
          }
          this.chart.reinit();
        });
      }
    });
  }

  changeChartType() {
    this.chart.type = this.chartType;
    switch (this.chartType) {
      case 'bar':
        this.vnfInstances.forEach(vnf => {
          vnf['chartData'].backgroundColor = vnf['chartData'].borderColor.replace(vnf['chartData'].borderColor.split(',')[3], ' 1)');
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
