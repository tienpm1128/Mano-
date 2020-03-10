import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { AlarmService } from './alarm.service';
import { IAlarm } from 'app/shared/model/alarm.model';
import { Table } from 'primeng/table';
import { MessageService, ConfirmationService } from 'primeng/api';
import { NsInstanceService } from 'app/entities/network-service/ns-instance/ns-instance.service';
import { AccountService } from 'app/core';
import { IVnfInstance } from 'app/shared/model/vnfInstance.model';
import { OverviewService } from 'app/entities/overview/overview.service';

@Component({
  selector: 'jhi-alarm',
  templateUrl: './alarm.component.html',
  styleUrls: ['./alarm.component.scss']
})
export class AlarmComponent implements OnInit, AfterViewInit {
  @ViewChild('severity', { static: true }) severity;
  @ViewChild('dt', { static: true }) table: Table;

  isAdmin = false;
  totalNsdChart: any;
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
    responsive: true,
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

  times = [];
  today = new Date().getTime();

  data: IAlarm[];

  loggedVims = [];
  charts = [];

  chartOptions = {
    legend: {
      display: false
    }
  };

  constructor(
    private alarmService: AlarmService,
    private messageService: MessageService,
    private nsInstanceService: NsInstanceService,
    private accountService: AccountService,
    private confirmationService: ConfirmationService,
    private overviewService: OverviewService
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
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');

    for (let i = 0; i < 30; i++) {
      const range = [];
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

    this.loadData();
  }

  loadData() {
    this.alarmService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.data = res.body.data;
          this.data.forEach(alarm => {
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
                      summary: 'Alarm',
                      detail: nsInstance.body.message,
                      life: 10000
                    });
                  }
                },
                nsInstance => {
                  this.messageService.add({
                    severity: 'error',
                    summary: 'Alarm',
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
          summary: 'Total Open stack instances',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
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

  filterChart(filter: any) {
    const filteredData = this.data.filter(alarm => {
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

  confirmEdit(idAlarm: string) {
    this.confirmationService.confirm({
      header: 'Acknowledgement Alarm Confirm',
      accept: () => {
        this.alarmService.update(idAlarm, { ackState: 'ACKNOWLEDGED' }).subscribe(
          res => {
            if (res.body.errorCode === '00') {
              this.messageService.add({ severity: 'success', summary: 'Alarm', detail: 'Update successfully!', life: 10000 });
              this.loadData();
            } else {
              this.messageService.add({ severity: 'error', summary: 'Alarm', detail: res.body.message, life: 10000 });
            }
          },
          res => {
            this.messageService.add({ severity: 'error', summary: 'Alarm', detail: res.error.status + ' ' + res.error.error, life: 10000 });
          }
        );
      }
    });
  }
}
