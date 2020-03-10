import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { OverviewService } from 'app/entities/overview/overview.service';
import { MessageService } from 'primeng/api';
import { AlarmService } from 'app/entities/fault-management/alarm/alarm.service';
import { IAlarm } from 'app/shared/model/alarm.model';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';
import { IVnfInstance } from 'app/shared/model/vnfInstance.model';
import { Table } from 'primeng/table';
import { VimInstanceService } from 'app/entities/resource-management/vim-instance/vim-instance.service';
import { AccountService, StateStorageService } from 'app/core';
import { ITenant } from 'app/shared/model/tenant.model';
import { TenantService } from 'app/entities/tenant/tenant.service';
import { JhiEventManager } from 'ng-jhipster';
import { PmJobsService } from 'app/entities/performance/vim/pm-jobs/pm-jobs.service';
import { Router } from '@angular/router';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { UIChart } from 'primeng/chart';
const randomColor = require('randomcolor');

@Component({
  selector: 'jhi-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit, AfterViewInit {
  @ViewChild('severity', { static: true }) severity;
  @ViewChild('dt', { static: true }) table: Table;
  @ViewChild('nsInstanceChart', { static: true }) nsInstanceChart: UIChart;

  isAdmin = false;
  isTenant = false;
  tenants: ITenant[];
  totalNsdChart: any;
  hypervisors = [];
  vimThresholds: any;
  chartNsdOptions: any;
  totalVnfdChart: any;
  chartVnfdOptions: any;
  totalNsInstanceChart: any;
  chartNsInstanceOptions: any;
  totalVnfInstanceChart: any;
  chartVnfInstanceOptions: any;
  totalVimChart: any;
  chartVimOptions: any;
  severityChart: any;
  eventTypeChart: any;
  faultChart: any;
  chartPlugin = [
    {
      beforeInit: (chart: any) => {
        chart.data.labels.forEach(function(e, i, a) {
          if (/\s/g.test(e)) {
            a[i] = e.split(/\s/g);
          }
        });
      }
    }
  ];
  barChartOptions = {
    legend: {
      display: false
    },
    scales: {
      xAxes: [
        {
          barThickness: 24,
          gridLines: {
            display: true
          },
          ticks: {
            beginAtZero: true,
            fontSize: 10,
            precision: 0
          }
        }
      ],
      yAxes: [
        {
          gridLines: {
            display: true
          },
          ticks: {
            beginAtZero: true,
            precision: 0
          }
        }
      ]
    }
  };
  arrColors = ['#FFFFFF', 'rgba(255, 255, 255, 0.6)', 'rgba(255, 255, 255, 0.3)'];

  times = [];
  today = new Date().getTime();

  alarms: IAlarm[];

  loggedVims = [];
  charts = [];
  chartOptions = {
    legend: {
      display: false
    }
  };
  thresholds: any;

  constructor(
    private overviewService: OverviewService,
    private messageService: MessageService,
    private alarmService: AlarmService,
    private nsInstanceService: NsInstanceService,
    private vimInstanceService: VimInstanceService,
    private accountService: AccountService,
    private tenantService: TenantService,
    private eventManager: JhiEventManager,
    private pmJobService: PmJobsService,
    private router: Router,
    private sessionStorage: SessionStorageService,
    private stateStorageService: StateStorageService,
    private localStorageService: LocalStorageService
  ) {}

  ngAfterViewInit(): void {
    this.table.filterConstraints.in = (value, filter): boolean => {
      if (filter === undefined || filter === null || filter[0] === '') {
        return true;
      }

      if (value === undefined || value === null) {
        return false;
      }

      value = new Date(value).getTime();
      return value >= filter[0] && value <= filter[1];
    };
  }

  ngOnInit() {
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN') || this.accountService.hasAuthority('PROVIDER_MEMBER');
    this.isTenant = this.accountService.hasAuthority('TENANT_ADMIN') || this.accountService.hasAuthority('TENANT_MEMBER');

    for (let i = 0; i < 7; i++) {
      const range: number[] = [];
      const now = new Date();
      now.setDate(now.getDate() - i);
      now.setHours(0);
      now.setMinutes(0);
      now.setSeconds(0);
      now.setMilliseconds(0);
      range.push(now.getTime());
      now.setHours(23);
      now.setMinutes(59);
      now.setSeconds(59);
      now.setMilliseconds(999);
      range.push(now.getTime());
      this.times.push(range);
    }

    this.overviewService.getTotalNsd().subscribe(
      res => {
        if (res.body.errorCode === '00' && this.isAdmin) {
          if (this.isAdmin) {
            let arrLabels = [];
            let arrDatas = [];
            const borders = new Array(res.body.data.tenants.length > 0 ? res.body.data.tenants.length : 0).fill(0);
            res.body.data.tenants.forEach(tenant => {
              arrLabels = arrLabels.concat(Object.keys(tenant));
              arrDatas = arrDatas.concat(Object.values(tenant));
            });
            this.chartNsdOptions = {
              cutoutPercentage: 70,
              legend: {
                position: 'bottom',
                labels: {
                  fontColor: 'white'
                },
                display: false
              },
              elements: {
                center: {
                  text: res.body.data.total,
                  color: 'white',
                  fontStyle: 'SFProText-Regular',
                  sidePadding: 50
                }
              },
              tooltips: {
                callbacks: {
                  label: (tooltipItem, data) => {
                    const dataset = data.datasets[tooltipItem.datasetIndex];
                    const total = dataset.data.reduce((previousValue, currentVal) => {
                      return previousValue + currentVal;
                    });
                    const currentValue = parseFloat(dataset.data[tooltipItem.index].toFixed(2));
                    const percentage = parseFloat(((currentValue / total) * 100).toFixed(2));
                    return `${currentValue}(${percentage}%)`;
                  }
                }
              }
            };
            this.totalNsdChart = {
              labels: arrLabels,
              datasets: [
                {
                  data: arrDatas,
                  backgroundColor: this.arrColors,
                  hoverBackgroundColor: this.arrColors,
                  borderWidth: borders
                }
              ]
            };
          } else if (this.isTenant) {
            this.totalNsdChart = res.body.data.total;
          }
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Total NSD',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Total NSD',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
    this.overviewService.getTotalVnfd().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          if (this.isAdmin) {
            let arrLabels = [];
            let arrDatas = [];
            const borders = new Array(res.body.data.tenants.length > 0 ? res.body.data.tenants.length : 0).fill(0);
            res.body.data.tenants.forEach(tenant => {
              arrLabels = arrLabels.concat(Object.keys(tenant));
              arrDatas = arrDatas.concat(Object.values(tenant));
            });
            this.chartVnfdOptions = {
              cutoutPercentage: 70,
              legend: {
                position: 'bottom',
                labels: {
                  fontColor: 'white'
                },
                display: false
              },
              elements: {
                center: {
                  text: res.body.data.total,
                  color: 'white',
                  fontStyle: 'SFProText-Regular',
                  sidePadding: 50
                }
              },
              tooltips: {
                callbacks: {
                  label: (tooltipItem, data) => {
                    const dataset = data.datasets[tooltipItem.datasetIndex];
                    const total = dataset.data.reduce((previousValue, currentVal) => {
                      return previousValue + currentVal;
                    });
                    const currentValue = parseFloat(dataset.data[tooltipItem.index].toFixed(2));
                    const percentage = parseFloat(((currentValue / total) * 100).toFixed(2));
                    return `${currentValue}(${percentage}%)`;
                  }
                }
              }
            };
            this.totalVnfdChart = {
              labels: arrLabels,
              datasets: [
                {
                  data: arrDatas,
                  backgroundColor: this.arrColors,
                  hoverBackgroundColor: this.arrColors,
                  borderWidth: borders
                }
              ]
            };
          } else if (this.isTenant) {
            this.totalVnfdChart = res.body.data.total;
          }
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Total Vnfd',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Total VNFD',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
    this.overviewService.getTotalNsInstance().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          if (this.isAdmin) {
            let arrLabels = [];
            let arrDatas = [];
            const borders = new Array(res.body.data.tenants.length > 0 ? res.body.data.tenants.length : 0).fill(0);
            res.body.data.tenants.forEach(tenant => {
              arrLabels = arrLabels.concat(Object.keys(tenant));
              arrDatas = arrDatas.concat(Object.values(tenant));
            });
            this.chartNsInstanceOptions = {
              cutoutPercentage: 70,
              legend: {
                position: 'bottom',
                labels: {
                  fontColor: 'white'
                },
                display: false
              },
              elements: {
                center: {
                  text: res.body.data.total,
                  color: 'white',
                  fontStyle: 'SFProText-Regular',
                  sidePadding: 50
                }
              },
              tooltips: {
                callbacks: {
                  label: (tooltipItem, data) => {
                    const dataset = data.datasets[tooltipItem.datasetIndex];
                    const total = dataset.data.reduce(function(previousValue, currentVal) {
                      return previousValue + currentVal;
                    });
                    const currentValue = parseFloat(dataset.data[tooltipItem.index].toFixed(2));
                    const percentage = parseFloat(((currentValue / total) * 100).toFixed(2));
                    return currentValue + '(' + percentage + '%)';
                  }
                }
              }
            };
            this.totalNsInstanceChart = {
              labels: arrLabels,
              datasets: [
                {
                  data: arrDatas,
                  backgroundColor: this.arrColors,
                  hoverBackgroundColor: this.arrColors,
                  borderWidth: borders
                }
              ]
            };
          } else if (this.isTenant) {
            this.totalNsInstanceChart = res.body.data.total;
          }
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Total Ns Instance',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Total Ns Instance',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
    this.overviewService.getTotalVnfInstance().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          if (this.isAdmin) {
            let arrLabels = [];
            let arrDatas = [];
            const borders = new Array(res.body.data.tenants.length > 0 ? res.body.data.tenants.length : 0).fill(0);
            const arrColors = ['#FFFFFF', 'rgba(255, 255, 255, 0.6)', 'rgba(255, 255, 255, 0.3)'];
            res.body.data.tenants.forEach(tenant => {
              arrLabels = arrLabels.concat(Object.keys(tenant));
              arrDatas = arrDatas.concat(Object.values(tenant));
            });
            this.chartVnfInstanceOptions = {
              cutoutPercentage: 70,
              legend: {
                position: 'bottom',
                labels: {
                  fontColor: 'white'
                },
                display: false
              },
              elements: {
                center: {
                  text: res.body.data.total,
                  color: 'white',
                  fontStyle: 'SFProText-Regular',
                  sidePadding: 50
                }
              },
              tooltips: {
                callbacks: {
                  label: (tooltipItem, data) => {
                    const dataset = data.datasets[tooltipItem.datasetIndex];
                    const total = dataset.data.reduce(function(previousValue, currentVal) {
                      return previousValue + currentVal;
                    });
                    const currentValue = parseFloat(dataset.data[tooltipItem.index].toFixed(2));
                    const percentage = parseFloat(((currentValue / total) * 100).toFixed(2));
                    return `${currentValue}(${percentage}%)`;
                  }
                }
              }
            };
            this.totalVnfInstanceChart = {
              labels: arrLabels,
              datasets: [
                {
                  data: arrDatas,
                  backgroundColor: arrColors,
                  hoverBackgroundColor: arrColors,
                  borderWidth: borders
                }
              ]
            };
          } else if (this.isTenant) {
            this.totalNsInstanceChart = res.body.data.total;
          }
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Total VNF instances',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Total VNF instances',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
    this.overviewService.getTotalVim().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          if (this.isAdmin) {
            let arrLabels = [];
            let arrDatas = [];
            const borders = new Array(res.body.data.tenants.length > 0 ? res.body.data.tenants.length : 0).fill(0);
            const arrColors = ['#FFFFFF', 'rgba(255, 255, 255, 0.6)', 'rgba(255, 255, 255, 0.3)'];
            res.body.data.tenants.forEach(tenant => {
              arrLabels = arrLabels.concat(Object.keys(tenant));
              arrDatas = arrDatas.concat(Object.values(tenant));
            });
            this.chartVimOptions = {
              cutoutPercentage: 70,
              legend: {
                position: 'bottom',
                labels: {
                  fontColor: 'white'
                },
                display: false
              },
              elements: {
                center: {
                  text: res.body.data.total,
                  color: 'white',
                  fontStyle: 'SFProText-Regular',
                  sidePadding: 50
                }
              },
              tooltips: {
                callbacks: {
                  label: (tooltipItem, data) => {
                    const dataset = data.datasets[tooltipItem.datasetIndex];
                    const total = dataset.data.reduce(function(previousValue, currentVal) {
                      return previousValue + currentVal;
                    });
                    const currentValue = parseFloat(dataset.data[tooltipItem.index].toFixed(2));
                    const percentage = parseFloat(((currentValue / total) * 100).toFixed(2));
                    return `${currentValue}(${percentage}%)`;
                  }
                }
              }
            };
            this.totalVimChart = {
              labels: arrLabels,
              datasets: [
                {
                  data: arrDatas,
                  backgroundColor: arrColors,
                  hoverBackgroundColor: arrColors,
                  borderWidth: borders
                }
              ]
            };
          } else if (this.isTenant) {
            this.totalVimChart = res.body.data.total;
          }
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Total Open stack',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Total Open stack instances',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );

    this.alarmService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.alarms = res.body.data;
          this.alarms.forEach(alarm => {
            if (alarm.managedObjectId) {
              this.nsInstanceService.find(alarm.managedObjectId).subscribe(
                nsInstance => {
                  if (nsInstance.body.errorCode === '00') {
                    alarm.rootCauseFaultyComponent['nsInstance'] = nsInstance.body.data;
                    alarm.rootCauseFaultyComponent['nsInstance']['vnfInstances'] = alarm.rootCauseFaultyComponent['nsInstance'][
                      'vnfInstances'
                    ].filter((vnf: IVnfInstance) => {
                      return vnf.id === alarm.rootCauseFaultyComponent.faultyVnfInstanceId;
                    })[0];
                  } else {
                    this.messageService.add({
                      severity: 'error',
                      summary: 'Ns Instance',
                      detail: nsInstance.body.message,
                      life: 10000
                    });
                  }
                },
                nsInstance => {
                  this.messageService.add({
                    severity: 'error',
                    summary: 'Ns Instance',
                    detail: nsInstance.error.status + ' ' + nsInstance.error.message,
                    life: 10000
                  });
                }
              );
            }
          });
          this.filterChart(new Date().getTime());
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Alarm',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Alarm',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );

    const currentLogged = this.accountService.getCurrentLoggedVimUser();
    if (currentLogged) {
      currentLogged.forEach(logged => {
        if (logged['vimId']) {
          const vimProjects = this.localStorageService.retrieve('vimProjects');
          let usedVCpu = 0;
          let vCpu = 0;
          let usedMemory = 0;
          let memory = 0;
          let usedStorage = 0;
          let storage = 0;
          const loggedVim = {};
          vimProjects.forEach((vimProject, index) => {
            this.vimInstanceService.getResource(logged['vimId'], vimProject.projectId).subscribe(
              res => {
                if (res.errorCode === '00') {
                  loggedVim['vimName'] = logged['vimName'];
                  res.data['hypervisor'].forEach(hyper => {
                    usedVCpu += hyper['virtualUsedCPU'];
                    vCpu += hyper['virtualCPU'];
                    usedMemory += hyper['localMemoryUsed'];
                    memory += hyper['localMemory'];
                    usedStorage += hyper['localDiskUsed'];
                    storage += hyper['localDisk'];
                  });
                  if (index === vimProjects.length - 1) {
                    const cpuChart = {
                      labels: ['Used', 'Free'],
                      datasets: [
                        {
                          data: [usedVCpu, vCpu - usedVCpu],
                          backgroundColor: ['#49CBAC', '#FFCA43']
                        }
                      ]
                    };
                    const memoryChart = {
                      labels: ['Used', 'Free'],
                      datasets: [
                        {
                          data: [usedMemory, memory - usedMemory],
                          backgroundColor: ['#49CBAC', '#FFCA43']
                        }
                      ]
                    };
                    const storageChart = {
                      labels: ['Used', 'Free'],
                      datasets: [
                        {
                          data: [usedStorage, storage - usedStorage],
                          backgroundColor: ['#49CBAC', '#FFCA43']
                        }
                      ]
                    };
                    loggedVim['cpuChart'] = cpuChart;
                    loggedVim['memoryChart'] = memoryChart;
                    loggedVim['storageChart'] = storageChart;
                    loggedVim['vimId'] = logged['vimId'];
                    loggedVim['hypervisor'] = res.data['hypervisor'].length;
                    loggedVim['vnfInstances'] = res.data['vnfInstances'];
                    loggedVim['nsInstances'] = res.data['nsInstances'];
                    this.loggedVims.push(loggedVim);
                    this.vimInstanceService.getNetworkTotal(logged['vimId']).subscribe(
                      response => {
                        if (response.body.errorCode === '00') {
                          this.loggedVims[this.loggedVims.indexOf(loggedVim)]['networkChart'] = {
                            labels: ['Used', 'Free'],
                            datasets: [
                              {
                                data: [response.body.data.used, response.body.data.total - response.body.data.used],
                                backgroundColor: ['#49CBAC', '#FFCA43']
                              }
                            ]
                          };
                        } else {
                          this.messageService.add({
                            severity: 'error',
                            summary: 'Network',
                            detail: response.body.message,
                            life: 10000
                          });
                        }
                      },
                      response => {
                        this.messageService.add({
                          severity: 'error',
                          summary: 'Network',
                          detail: response.error.status + ' ' + response.error.message,
                          life: 10000
                        });
                      }
                    );
                    this.vimInstanceService.getThreshold(logged['vimId']).subscribe(
                      thresholdRes => {
                        if (thresholdRes.body.errorCode === '00') {
                          this.loggedVims[this.loggedVims.indexOf(loggedVim)]['thresholds'] = thresholdRes.body.data;
                        } else {
                          this.messageService.add({
                            severity: 'error',
                            summary: 'Threshold',
                            detail: thresholdRes.body.message,
                            life: 10000
                          });
                        }
                      },
                      thresholdRes => {
                        this.messageService.add({
                          severity: 'error',
                          summary: 'Threshold',
                          detail: thresholdRes.error.status + ' ' + thresholdRes.error.message,
                          life: 10000
                        });
                      }
                    );
                    this.vimInstanceService.getResourceQuota(logged['vimId']).subscribe(
                      quotaRes => {
                        if (quotaRes.body.errorCode === '00') {
                          this.loggedVims[this.loggedVims.indexOf(loggedVim)]['resourceQuota'] = quotaRes.body.data;
                        } else {
                          this.messageService.add({
                            severity: 'error',
                            summary: 'Resource quota',
                            detail: quotaRes.body.message,
                            life: 10000
                          });
                        }
                      },
                      quotaRes => {
                        this.messageService.add({
                          severity: 'error',
                          summary: 'Threshold',
                          detail: quotaRes.error.status + ' ' + quotaRes.error.message,
                          life: 10000
                        });
                      }
                    );
                  }
                } else {
                  this.messageService.add({
                    severity: 'error',
                    summary: 'Open stack instances',
                    detail: res.message,
                    life: 10000
                  });
                }
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Total Open stack instances',
                  detail: res.error.status + ' ' + res.error.message,
                  life: 10000
                });
              }
            );
          });
          this.vimInstanceService.getThreshold(logged['vimId']).subscribe(
            thresholdRes => {
              if (thresholdRes.body.data) {
                if (thresholdRes.body.errorCode === '00' && thresholdRes.body.data.criteria) {
                  this.vimThresholds = thresholdRes.body.data.criteria;
                }
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Threshold',
                  detail: thresholdRes.body.message,
                  life: 10000
                });
              }
            },
            thresholdRes => {
              this.messageService.add({
                severity: 'error',
                summary: 'Threshold',
                detail: thresholdRes.error.status + ' ' + thresholdRes.error.message,
                life: 10000
              });
            }
          );
        }
      });
    }

    this.tenantService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.tenants = res.body.data;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Tenant',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Tenant',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );

    this.eventManager.subscribe('notifications', res => {
      const notificationData = res.content;
      if (notificationData.notificationType === 'PerformanceInformationAvailableNotification') {
        const hyperIndex = this.hypervisors.indexOf(notificationData.objectInstanceId);
        if (hyperIndex !== -1) {
          this.pmJobService.findReportByUrl(notificationData._link.performanceReport.href).subscribe(
            report => {
              if (report.body.errorCode === '00') {
                this.hypervisors[hyperIndex]['reportData'] = report.body.data;
              } else {
                this.messageService.add({
                  severity: 'error',
                  summary: 'PM Job report',
                  detail: report.body.message,
                  life: 10000
                });
              }
            },
            report => {
              this.messageService.add({
                severity: 'error',
                summary: 'PM Job report',
                detail: report.error.status + ' ' + report.error.message,
                life: 10000
              });
            }
          );
        }
      }
    });

    this.eventManager.subscribe('logoutVim', res => {
      this.loggedVims.forEach(vim => {
        if (vim['vimId'] === res.content) {
          this.loggedVims.splice(this.loggedVims.indexOf(vim), 1);
        }
      });
    });
  }

  getColor(eventType: string) {
    switch (eventType) {
      case 'CRITICAL':
        return '#f9e3e3';
      case 'WARNING':
        return '#fbeaea';
      case 'MAJOR':
        return '#FCEDDF';
      case 'MINOR':
        return '#f9ede3';
      case 'INDETERMINATE':
        return '#FFF9E6';
      default:
        return 'white';
    }
  }

  filterChart(filter?: any) {
    const filteredData = this.alarms.filter(alarm => {
      let timeDate = new Date();
      if (filter) {
        timeDate = filter.toString().indexOf(',') !== -1 ? new Date(parseInt(filter.toString().split(',')[0], 10)) : new Date(filter);
      }
      const timeDateStr = `${timeDate.getDate()}/${timeDate.getMonth() + 1}/${timeDate.getFullYear()}`;

      const eventDate = new Date(alarm.eventTime);
      const eventDateStr = `${eventDate.getDate()}/${eventDate.getMonth() + 1}/${eventDate.getFullYear()}`;
      return eventDateStr === timeDateStr;
    });
    const severityLabels = ['CRITICAL', 'MAJOR', 'MINOR', 'WARNING', 'INDETERMINATE', 'CLEARED'];
    const eventTypeLabels = ['COMMUNICATIONS ALARM', 'QOS ALARM', 'PROCESSING ERROR ALARM', 'ENVIRONMENTAL ALARM', 'EQUIPMENT ALARM'];
    const faultLabels = ['PHYSICAL MANO', 'PHYSICAL VIM', 'VNF INSTANCE', 'VIRTUAL COMPUTE', 'SERVICE'];

    const arrSeverityData = [];
    arrSeverityData.push(filteredData.filter(alarm => alarm.perceivedSeverity === 'CRITICAL').length);
    arrSeverityData.push(filteredData.filter(alarm => alarm.perceivedSeverity === 'MAJOR').length);
    arrSeverityData.push(filteredData.filter(alarm => alarm.perceivedSeverity === 'MINOR').length);
    arrSeverityData.push(filteredData.filter(alarm => alarm.perceivedSeverity === 'WARNING').length);
    arrSeverityData.push(filteredData.filter(alarm => alarm.perceivedSeverity === 'INDETERMINATE').length);
    arrSeverityData.push(filteredData.filter(alarm => alarm.perceivedSeverity === 'CLEARED').length);
    this.severityChart = {
      labels: severityLabels,
      datasets: [
        {
          data: arrSeverityData,
          backgroundColor: ['#E73B35', '#EA5A36', '#ED7F37', '#F0A739', '#F2BE39', '#F4D43A']
        }
      ]
    };

    const arrEventTypeData = [];
    arrEventTypeData.push(filteredData.filter(alarm => alarm.eventType === 'COMMUNICATIONS_ALARM').length);
    arrEventTypeData.push(filteredData.filter(alarm => alarm.eventType === 'QOS_ALARM').length);
    arrEventTypeData.push(filteredData.filter(alarm => alarm.eventType === 'PROCESSING_ERROR_ALARM').length);
    arrEventTypeData.push(filteredData.filter(alarm => alarm.eventType === 'ENVIRONMENTAL_ALARM').length);
    arrEventTypeData.push(filteredData.filter(alarm => alarm.eventType === 'EQUIPMENT_ALARM').length);
    this.eventTypeChart = {
      labels: eventTypeLabels,
      datasets: [
        {
          data: arrEventTypeData,
          backgroundColor: '#2ed6ed'
        }
      ]
    };

    const arrFaultData = [];
    arrFaultData.push(filteredData.filter(alarm => alarm.faultType === 'PHYSICAL_MANO').length);
    arrFaultData.push(filteredData.filter(alarm => alarm.faultType === 'PHYSICAL_VIM').length);
    arrFaultData.push(filteredData.filter(alarm => alarm.faultType === 'VNF_INSTANCE').length);
    arrFaultData.push(filteredData.filter(alarm => alarm.faultType === 'VIRTUAL_COMPUTE').length);
    arrFaultData.push(filteredData.filter(alarm => alarm.faultType === 'SERVICE').length);
    this.faultChart = {
      labels: faultLabels,
      datasets: [
        {
          data: arrFaultData,
          backgroundColor: '#5ADAA4'
        }
      ]
    };
  }

  toLoginVim() {
    this.stateStorageService.storeUrl(this.router.url);
    this.router.navigate(['/vims']);
  }
}
