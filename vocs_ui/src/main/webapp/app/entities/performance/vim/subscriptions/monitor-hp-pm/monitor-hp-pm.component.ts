import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { INsSubscription } from 'app/shared/model/nsSubscription.model';
import { NsPmSubscriptionsService } from 'app/entities/performance/ns/subscriptions/ns-pm-subscriptions.service';
import { MessageService } from 'primeng/api';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';
import { IVnfInstance } from 'app/shared/model/vnfInstance.model';
import { JhiEventManager } from 'ng-jhipster';

const randomColor = require('randomcolor');

@Component({
  selector: 'jhi-monitor-ns-pm',
  templateUrl: './monitor-hp-pm.component.html',
  styleUrls: ['./monitor-hp-pm.component.scss']
})
export class MonitorHpPmComponent implements OnInit {
  nspmSubscription: INsSubscription;
  vnfInstances: IVnfInstance[];
  selectedVnfInstances: IVnfInstance[];

  chartType = 'line';

  thresholdChart: any;

  constructor(
    private activatedRoute: ActivatedRoute,
    private nsSubscriptionsService: NsPmSubscriptionsService,
    private messageService: MessageService,
    private nsInstanceService: NsInstanceService,
    private eventManager: JhiEventManager,
    private router: Router
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(
      ({ nspmSubscription }) => {
        this.nspmSubscription = nspmSubscription;
        this.nsInstanceService.find(this.nspmSubscription.id).subscribe(
          res => {
            if (res.body.errorCode === '00') {
              this.vnfInstances = res.body.data.vnfInstances;
              this.vnfInstances.forEach(vnf => {
                vnf['chartData'] = {
                  label: vnf.vnfInstanceName,
                  data: [],
                  borderColor: randomColor({
                    luminosity: 'light',
                    count: 1
                  })[0]
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
        this.nsSubscriptionsService.getDetailPmJob(this.nspmSubscription.filter['nsInstanceSubscriptionFilter'].nsInstanceIds[0]).subscribe(
          res => {
            if (res.body.errorCode === '00') {
              this.nspmSubscription['pmJobDetail'] = res.body.data;
            } else {
              this.messageService.add({
                severity: 'error',
                summary: 'PM Job',
                detail: res.body.message,
                life: 10000
              });
            }
          },
          res => {
            this.messageService.add({
              severity: 'error',
              summary: 'PM Job',
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
    const chartLabels = [];
    const chartData = [];
    this.eventManager.subscribe('notifications', res => {
      const notificationData = res.content;
      if (notificationData.notificationType === 'ThresholdCrossedNotification') {
        if (this.selectedVnfInstances) {
          this.selectedVnfInstances.forEach(vnf => {
            if (vnf.id === notificationData.objectInstanceId) {
              chartLabels.forEach(time => {
                if (notificationData.timeStamp !== time) {
                  chartLabels.push(notificationData.timeStamp);
                  vnf['chartData'].data.push(notificationData.performanceValue);
                } else {
                  const timeIndex = chartLabels.indexOf(time);
                  vnf['chartData'].data[timeIndex] = notificationData.performanceValue;
                }
              });
            }
          });
          this.thresholdChart = {
            labels: chartLabels,
            datasets: chartData
          };
        }
      }
    });
  }
}
