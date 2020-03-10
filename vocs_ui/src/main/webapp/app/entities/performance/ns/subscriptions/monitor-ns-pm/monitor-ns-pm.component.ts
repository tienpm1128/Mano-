import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { INsSubscription } from 'app/shared/model/nsSubscription.model';
import { MessageService } from 'primeng/api';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';
import { IVnfInstance } from 'app/shared/model/vnfInstance.model';
import { JhiEventManager } from 'ng-jhipster';
import { UIChart } from 'primeng/chart';
import { NsPmSubscriptionsService } from 'app/entities/performance/ns/subscriptions/ns-pm-subscriptions.service';
import { PmJobsService } from 'app/entities/performance/ns/pm-jobs/pm-jobs.service';

const randomColor = require('randomcolor');

@Component({
  selector: 'jhi-monitor-ns-pm',
  templateUrl: './monitor-ns-pm.component.html',
  styleUrls: ['./monitor-ns-pm.component.scss']
})
export class MonitorNsPmComponent implements OnInit {
  @ViewChildren(UIChart) charts: QueryList<UIChart>;

  nspmSubscription: INsSubscription;
  vnfInstances: IVnfInstance[];
  selectedVnfInstances: IVnfInstance[];
  chartColors = [];
  chartMetrics = new Map();

  constructor(
    private activatedRoute: ActivatedRoute,
    private nsSubscriptionsService: NsPmSubscriptionsService,
    private messageService: MessageService,
    private nsInstanceService: NsInstanceService,
    private eventManager: JhiEventManager,
    private pmJobService: PmJobsService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(
      ({ nspmSubscription }) => {
        this.nspmSubscription = nspmSubscription;
        this.nsInstanceService.find(this.nspmSubscription.id).subscribe(
          res => {
            if (res.body.errorCode === '00') {
              this.vnfInstances = res.body.data.vnfInstances;
              this.chartColors = randomColor({
                luminosity: 'light',
                count: this.vnfInstances.length,
                format: 'rgba',
                alpha: 0.4
              });
            } else {
              this.messageService.add({
                severity: 'error',
                summary: 'NS Instance',
                detail: res.body.message,
                life: 10000
              });
            }
          },
          res => {
            this.messageService.add({
              severity: 'error',
              summary: 'NS Instance',
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
    this.eventManager.subscribe('notifications', res => {
      const notificationData = res.content;
      const pmJobId = notificationData._link.performanceReport.href.split('/pm/extended/v1/internal_pm_jobs/')[1].split('/reports/')[0];
      const reportId = notificationData._link.performanceReport.href.split('/pm/extended/v1/internal_pm_jobs/')[1].split('/reports/')[1];
      if (
        notificationData.notificationType === 'PerformanceInformationAvailableNotification' &&
        this.selectedVnfInstances &&
        /^.*\/pm\/extended\/v1\/internal_pm_jobs\/[\w\d]+\/reports\/[\w\d]+/g.test(notificationData._link.performanceReport.href)
      ) {
        this.pmJobService.findReportById(pmJobId, reportId).subscribe(response => {
          if (response.body.errorCode === '00') {
            if (response.body.data) {
              const reportRes = response.body.data.entries;
              if (reportRes.length > 0) {
                const metric = reportRes[0].performanceMetric;
                const timeStamp = reportRes[0].performanceValue.timeStamp;

                if (!this.chartMetrics.has(metric)) {
                  const chartData = {
                    type: 'line',
                    labels: [],
                    options: {
                      title: {
                        text: metric
                      },
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
                  this.chartMetrics.set(metric, chartData);
                }

                const metricData = this.chartMetrics.get(metric);
                const timeIndex = metricData.labels.indexOf(timeStamp);
                let dataIndex = metricData.labels.length - 1;
                // neu timestamp chua co => tim vi tri thich hop de insert timestamp vao labels theo thu tu thoi gian tang dan
                if (timeIndex === -1) {
                  dataIndex++;
                  if (metricData.labels.length === 0) {
                    metricData.labels.push(timeStamp);
                    metricData.labels = [...metricData.labels];
                  } else {
                    metricData.labels.forEach((label, index) => {
                      if (timeStamp > label) {
                        if (index >= metricData.labels.length - 1) {
                          metricData.labels.push(timeStamp);
                          metricData.labels = [...metricData.labels];
                        }
                      } else {
                        metricData.labels.splice(index, 0, timeStamp);
                        dataIndex = index;
                        metricData.labels = [...metricData.labels];
                      }
                    });
                  }
                }

                let maxItems = 0;
                metricData.datasets.forEach(line => {
                  this.selectedVnfInstances.forEach(entry => {
                    if (line.label === entry.id) {
                      maxItems = maxItems < line.data.length ? line.data.length : maxItems;
                    }
                  });
                });

                reportRes.forEach(entry => {
                  this.selectedVnfInstances.forEach(selectedVNF => {
                    if (selectedVNF.id === entry.objectInstanceId) {
                      // TH them moi data
                      if (timeIndex === -1) {
                        let found = false;
                        // kiem tra VNF ID co trong du lieu chart hay chua? Neu co roi -> tim vi tri theo timestamp va chen du lieu vao.
                        metricData.datasets.forEach(line => {
                          if (line.label === entry.objectInstanceId) {
                            found = true;
                            if (dataIndex === metricData.labels.length - 1) {
                              line.data = [...line.data, entry.performanceValue.performanceValue];
                            } else {
                              line.data.splice(dataIndex, 0, entry.performanceValue.performanceValue);
                              line.data = [...line.data];
                            }
                          }
                        });
                        // Neu ko thi tao moi va chen vao datasets
                        if (!found) {
                          const dummyItems = new Array(maxItems + 1);
                          dummyItems.fill(null);
                          dummyItems.splice(dataIndex, 1, entry.performanceValue.performanceValue);
                          metricData.datasets.push({
                            label: entry.objectInstanceId,
                            data: [...dummyItems],
                            fill: false,
                            borderColor: this.chartColors[
                              this.vnfInstances.indexOf(this.vnfInstances.filter(item => item.id === entry.objectInstanceId)[0])
                            ],
                            backgroundColor: this.chartColors[
                              this.vnfInstances.indexOf(this.vnfInstances.filter(item => item.id === entry.objectInstanceId)[0])
                            ]
                          });
                        }
                      } else {
                        let found = false;
                        // TH Update data
                        metricData.datasets.forEach(line => {
                          if (line.label === entry.objectInstanceId) {
                            found = true;
                            line.data.splice(timeIndex, 1, entry.performanceValue.performanceValue);
                            line.data = [...line.data];
                          }
                        });

                        if (!found) {
                          // const dummyItems = new Array(maxItems);
                          // dummyItems.fill(null);
                          // metricData.datasets.push({
                          //   label: entry.objectInstanceId,
                          //   data: [...dummyItems, entry.performanceValue.performanceValue],
                          //   fill: false,
                          //   borderColor: this.chartColors[
                          //     this.vnfInstances.indexOf(this.vnfInstances.filter(item => item.id === entry.objectInstanceId)[0])
                          //     ],
                          //   backgroundColor: this.chartColors[
                          //     this.vnfInstances.indexOf(this.vnfInstances.filter(item => item.id === entry.objectInstanceId)[0])
                          //     ]
                          // });

                          const dummyItems = new Array(maxItems);
                          dummyItems.fill(null);
                          dataIndex === 0
                            ? dummyItems.splice(dataIndex, 1, entry.performanceValue.performanceValue)
                            : dummyItems.splice(dataIndex, 0, entry.performanceValue.performanceValue);
                          metricData.datasets.push({
                            label: entry.objectInstanceId,
                            data: [...dummyItems],
                            fill: false,
                            borderColor: this.chartColors[
                              this.vnfInstances.indexOf(this.vnfInstances.filter(item => item.id === entry.objectInstanceId)[0])
                            ],
                            backgroundColor: this.chartColors[
                              this.vnfInstances.indexOf(this.vnfInstances.filter(item => item.id === entry.objectInstanceId)[0])
                            ]
                          });
                        }
                      }
                    }
                  });
                });
                // metricData.datasets.forEach(line => {
                //   line.data = [...line.data];
                // });
                this.charts.forEach(chart => {
                  if (chart._data.options.title.text === metric) {
                    console.log(chart);
                    chart.refresh();
                  }
                });
              }
            }
          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'Pm Job report',
              detail: response.body.message,
              life: 10000
            });
          }
        });
      }
    });
  }

  changeChartType(type: string, metric: string) {
    this.charts.forEach(chart => {
      if (chart._data.options.title.text === metric) {
        chart.type = type;
        chart.reinit();
      }
    });
  }
}
