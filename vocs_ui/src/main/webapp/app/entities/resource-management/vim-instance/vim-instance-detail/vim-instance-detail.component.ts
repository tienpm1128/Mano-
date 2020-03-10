import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IVimInstance } from 'app/shared/model/vimInstance.model';
import { IProjects } from 'app/shared/model/projects.model';
import { LocalStorageService } from 'ngx-webstorage';
import { VimInstanceService } from 'app/entities/resource-management/vim-instance/vim-instance.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CommonUntil } from 'app/shared/util/commonUtil';
import { GrantVimRoleRequestModel } from 'app/shared/model/grantVimRoleRequest.model';
import { VimInstaceDetailService } from './vim-instance-detail.service';
import { IUserMano } from 'app/shared/model/userMano.model';
import { UserManoService } from '../../user-mano/user-mano.service';
import { JhiEventManager } from 'ng-jhipster';
import { PmJobsService } from 'app/entities/performance/vim/pm-jobs/pm-jobs.service';
import { RoleService } from 'app/entities/resource-management/vim-instance/role/role.service';
const randomColor = require('randomcolor');

@Component({
  selector: 'jhi-vim-instance-detail',
  templateUrl: './vim-instance-detail.component.html',
  styleUrls: ['./vim-instance-detail.component.scss']
})
export class VimInstanceDetailComponent implements OnInit, OnDestroy {
  @ViewChild('chart', { static: true }) chartInstance;

  isSaving = false;
  commonUtil = new CommonUntil();
  vimUser: string;
  vimInstance: IVimInstance;
  vimProjects: IProjects[];
  hypervisors = [];
  resourceQuota: any;
  vimThresholds: any;
  vimHostIps = [];
  isAdmin = false;
  usedvCPU = 0;
  totalvCPU = 0;
  usedMemory = 0;
  totalMemory = 0;
  usedLocalDisk = 0;
  totalLocalDisk = 0;
  vCpuChart: any;
  localDiskChart: any;
  memoryChart: any;
  chartOptions = {
    legend: {
      display: false
    }
  };
  computeVCpu: any;
  computeVM: any;
  computeRam: any;
  networkIP: any;
  networkPort: any;
  networkSubnet: any;
  storageSize: any;
  storageSnapshot: any;
  storageVolumn: any;
  tabOptions = {
    legend: {
      display: false
    }
  };
  grantVimRoleRequest = new GrantVimRoleRequestModel();
  isDisplayGrantVimRole = false;
  userManos: IUserMano[];
  vimId: any;

  refreshVimSub: any;

  constructor(
    private accountService: AccountService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private localStorageService: LocalStorageService,
    private vimInstanceService: VimInstanceService,
    private messageService: MessageService,
    private vimInstanceDetailService: VimInstaceDetailService,
    private userManoService: UserManoService,
    private eventManager: JhiEventManager,
    private pmJobService: PmJobsService,
    private confirmationService: ConfirmationService,
    private roleService: RoleService
  ) {
    this.resetForm();
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
    this.vimUser = this.accountService.getCurrentLoggedVimUser(this.vimId);
    if (!this.vimUser) {
      this.router.navigate(['/vims', this.vimId, 'login']);
    } else if (!this.vimUser['projectId']) {
      this.router.navigate(['/vims', this.vimId, 'user', this.vimUser['userId'], 'project']);
    }
  }

  ngOnInit() {
    this.userManoService.query().subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.userManos = res.body.data;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'User MANO',
            detail: res.body.message,
            life: 10000
          });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'User MANO',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.vimProjects = this.localStorageService.retrieve('vimProjects');
    this.activatedRoute.data.subscribe(
      ({ vimInstance }) => {
        const chartLabels = [];
        const vCpus = [];
        const memories = [];
        const localDisks = [];
        this.vimInstance = vimInstance;
        if (this.vimProjects) {
          let i = 0;
          const randomColors = randomColor({ luminosity: 'light', count: this.vimProjects.length });
          this.vimProjects.forEach(project => {
            let vCpu = 0;
            let usedVCpu = 0;
            let memory = 0;
            let usedMemory = 0;
            let localDisk = 0;
            let usedLocalDisk = 0;
            let runningVM = 0;
            this.vimInstanceService.getResource(this.vimId, project.projectId).subscribe(
              res => {
                if (res.errorCode === '00') {
                  chartLabels.push(project.projectName);
                  i++;

                  if (project.projectId === this.vimUser['projectId']) {
                    res.data.hypervisor.forEach(hyper => {
                      this.vimHostIps.push(hyper.hostIP);
                    });
                  }

                  if (res.data.hypervisor) {
                    this.hypervisors = res.data.hypervisor;
                    res.data.hypervisor.forEach(hyper => {
                      vCpu += hyper.virtualCPU;
                      memory += hyper.localMemory;
                      localDisk += hyper.localDisk;

                      usedVCpu += hyper.virtualUsedCPU;
                      usedMemory += hyper.localMemoryUsed;
                      usedLocalDisk += hyper.localDiskUsed;

                      this.usedvCPU += hyper.virtualUsedCPU;
                      this.totalvCPU += hyper.virtualCPU;
                      this.usedMemory += hyper.localMemoryUsed;
                      this.totalMemory += hyper.localMemory;
                      this.usedLocalDisk += hyper.localDiskUsed;
                      this.totalLocalDisk += hyper.localDisk;
                      runningVM += hyper.runningVM;
                    });
                  }

                  project['hypervisors'] = res.data.hypervisor;
                  project['runningVM'] = runningVM;
                  project['vCpuConfig'] = {
                    labels: ['Used', 'Free'],
                    datasets: [
                      {
                        data: [usedVCpu, vCpu - usedVCpu],
                        backgroundColor: ['#ED6E85', '#2ED6ED']
                      }
                    ]
                  };
                  project['memoryConfig'] = {
                    labels: ['Used', 'Free'],
                    datasets: [
                      {
                        data: [usedMemory, memory - usedMemory],
                        backgroundColor: ['#ED6E85', '#2ED6ED']
                      }
                    ]
                  };
                  project['localDiskConfig'] = {
                    labels: ['Used', 'Free'],
                    datasets: [
                      {
                        data: [usedLocalDisk, localDisk - usedLocalDisk],
                        backgroundColor: ['#ED6E85', '#2ED6ED']
                      }
                    ]
                  };
                  this.vimProjects[this.vimProjects.indexOf(project)] = project;
                  vCpus.push(usedVCpu);
                  memories.push(usedMemory);
                  localDisks.push(usedLocalDisk);
                  if (i === this.vimProjects.length) {
                    chartLabels.push('Free');
                    vCpus.push(this.totalvCPU - this.usedvCPU);
                    memories.push(this.totalMemory - this.usedMemory);
                    localDisks.push(this.totalLocalDisk - this.usedLocalDisk);
                    this.vCpuChart = {
                      labels: chartLabels,
                      datasets: [
                        {
                          data: vCpus,
                          backgroundColor: randomColors
                        }
                      ]
                    };
                    this.memoryChart = {
                      labels: chartLabels,
                      datasets: [
                        {
                          data: memories,
                          backgroundColor: randomColors
                        }
                      ]
                    };
                    this.localDiskChart = {
                      labels: chartLabels,
                      datasets: [
                        {
                          data: localDisks,
                          backgroundColor: randomColors
                        }
                      ]
                    };
                  }
                }
              },
              res => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Vim Instance',
                  detail: res.status + ' ' + res.message,
                  life: 10000
                });
              }
            );
          });
        }
      },
      () => {
        this.router.navigate(['/vims']);
      }
    );
    this.vimInstanceService.getCompute(this.vimId).subscribe(
      compute => {
        if (compute.body.errorCode === '00') {
          this.vimInstanceService.getComputeLeft(this.vimId).subscribe(
            computeLeft => {
              if (computeLeft.body.errorCode === '00') {
                this.computeVCpu = {
                  labels: ['Used', 'Free'],
                  datasets: [
                    {
                      data: [compute.body.data.numVCPUs - computeLeft.body.data.numVCPUs, computeLeft.body.data.numVCPUs],
                      backgroundColor: ['#49CBAC', '#FFCA43'],
                      hoverBackgroundColor: ['#49CBAC ', '#FFCA43']
                    }
                  ]
                };
                this.computeVM = {
                  labels: ['Used', 'Free'],
                  datasets: [
                    {
                      data: [compute.body.data.numVcInstance - computeLeft.body.data.numVcInstance, computeLeft.body.data.numVcInstance],
                      backgroundColor: ['#49CBAC ', '#FFCA43'],
                      hoverBackgroundColor: ['#49CBAC ', '#FFCA43']
                    }
                  ]
                };
                this.computeRam = {
                  labels: ['Used', 'Free'],
                  datasets: [
                    {
                      data: [compute.body.data.virtualMemSize - computeLeft.body.data.virtualMemSize, computeLeft.body.data.virtualMemSize],
                      backgroundColor: ['#49CBAC ', '#FFCA43'],
                      hoverBackgroundColor: ['#49CBAC ', '#FFCA43']
                    }
                  ]
                };
              }
            },
            computeLeft => {
              this.messageService.add({
                severity: 'error',
                summary: 'Vim Instance',
                detail: computeLeft.body.error.status + ' ' + computeLeft.body.error.message,
                life: 10000
              });
            }
          );
        }
      },
      compute => {
        this.messageService.add({
          severity: 'error',
          summary: 'Vim Instance',
          detail: compute.error.status + ' ' + compute.error.message,
          life: 10000
        });
      }
    );
    this.vimInstanceService.getNetwork(this.vimId).subscribe(
      network => {
        if (network.body.errorCode === '00') {
          this.vimInstanceService.getNetworkLeft(this.vimId).subscribe(
            networkLeft => {
              if (networkLeft.body.errorCode === '00') {
                this.networkIP = {
                  labels: ['Used', 'Free'],
                  datasets: [
                    {
                      data: [network.body.data.numPublicIps - networkLeft.body.data.numPublicIps, networkLeft.body.data.numPublicIps],
                      backgroundColor: ['#49CBAC ', '#FFCA43'],
                      hoverBackgroundColor: ['#49CBAC ', '#FFCA43']
                    }
                  ]
                };
                this.networkPort = {
                  labels: ['Used', 'Free'],
                  datasets: [
                    {
                      data: [network.body.data.numPorts - networkLeft.body.data.numPorts, networkLeft.body.data.numPorts],
                      backgroundColor: ['#49CBAC ', '#FFCA43'],
                      hoverBackgroundColor: ['#49CBAC ', '#FFCA43']
                    }
                  ]
                };
                this.networkSubnet = {
                  labels: ['Used', 'Free'],
                  datasets: [
                    {
                      data: [network.body.data.numSubnets - networkLeft.body.data.numSubnets, networkLeft.body.data.numSubnets],
                      backgroundColor: ['#49CBAC ', '#FFCA43'],
                      hoverBackgroundColor: ['#49CBAC ', '#FFCA43']
                    }
                  ]
                };
              }
            },
            networkLeft => {
              this.messageService.add({
                severity: 'error',
                summary: 'Vim Instance',
                detail: networkLeft.body.error.status + ' ' + networkLeft.body.error.message,
                life: 10000
              });
            }
          );
        }
      },
      network => {
        this.messageService.add({
          severity: 'error',
          summary: 'Vim Instance',
          detail: network.error.status + ' ' + network.error.message,
          life: 10000
        });
      }
    );
    this.vimInstanceService.getStorage(this.vimId).subscribe(
      storage => {
        if (storage.body.errorCode === '00') {
          this.vimInstanceService.getStorageLeft(this.vimId).subscribe(
            storageLeft => {
              if (storageLeft.body.errorCode === '00') {
                this.storageSize = {
                  labels: ['Used', 'Free'],
                  datasets: [
                    {
                      data: [storage.body.data.storageSize - storageLeft.body.data.storageSize, storageLeft.body.data.storageSize],
                      backgroundColor: ['#49CBAC ', '#FFCA43'],
                      hoverBackgroundColor: ['#49CBAC ', '#FFCA43']
                    }
                  ]
                };
                this.storageSnapshot = {
                  labels: ['Used', 'Free'],
                  datasets: [
                    {
                      data: [storage.body.data.numSnapshots - storageLeft.body.data.numSnapshots, storageLeft.body.data.numSnapshots],
                      backgroundColor: ['#49CBAC ', '#FFCA43'],
                      hoverBackgroundColor: ['#49CBAC ', '#FFCA43']
                    }
                  ]
                };
                this.storageVolumn = {
                  labels: ['Used', 'Free'],
                  datasets: [
                    {
                      data: [storage.body.data.numVolumns - storageLeft.body.data.numVolumns, storageLeft.body.data.numVolumns],
                      backgroundColor: ['#49CBAC ', '#FFCA43'],
                      hoverBackgroundColor: ['#49CBAC ', '#FFCA43']
                    }
                  ]
                };
              }
            },
            storageLeft => {
              this.messageService.add({
                severity: 'error',
                summary: 'Vim Instance',
                detail: storageLeft.error.status + ' ' + storageLeft.error.error,
                life: 10000
              });
            }
          );
        }
      },
      storage => {
        this.messageService.add({
          severity: 'error',
          summary: 'Vim Instance',
          detail: storage.error.status + ' ' + storage.error.message,
          life: 10000
        });
      }
    );
    this.vimInstanceService.getResourceQuota(this.vimId).subscribe(
      quota => {
        if (quota.body.errorCode === '00') {
          this.resourceQuota = quota.body.data;
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Resource Quota',
            detail: quota.body.message,
            life: 10000
          });
        }
      },
      quota => {
        this.messageService.add({
          severity: 'error',
          summary: 'Vim Instance',
          detail: quota.error.status + ' ' + quota.error.message,
          life: 10000
        });
      }
    );
    this.vimInstanceService.getThreshold(this.vimId).subscribe(
      thresholdRes => {
        if (thresholdRes.body.errorCode === '00' && thresholdRes.body.data && thresholdRes.body.data.criteria) {
          this.vimThresholds = thresholdRes.body.data.criteria;
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

    this.refreshVimSub = this.eventManager.subscribe('logoutVim', res => {
      if (this.vimId === res.content) {
        this.router.navigate(['/vims']);
      }
    });
  }

  resetForm() {
    this.grantVimRoleRequest.manoUserGrantedId = null;
    this.grantVimRoleRequest.vimRole = null;
  }

  logoutVim() {
    this.confirmationService.confirm({
      header: 'Confirm Logout',
      accept: () => {
        this.accountService.logoutVim(this.vimId);
        this.router.navigate(['/vims']);
      }
    });
  }

  grantVimRole() {
    this.isSaving = true;
    this.vimInstanceDetailService.grantVimRole(this.grantVimRoleRequest, this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.messageService.add({ severity: 'success', summary: 'Vim Instance', detail: 'Grant Vim role successfully!', life: 10000 });
          this.isSaving = false;
        } else {
          this.messageService.add({ severity: 'error', summary: 'Vim Instance', detail: res.body.message, life: 10000 });
          this.isSaving = false;
        }
        this.isSaving = false;
        this.isDisplayGrantVimRole = false;
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Vim Instance',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
        this.isSaving = false;
      }
    );
  }

  ngOnDestroy(): void {
    if (this.refreshVimSub) {
      this.eventManager.destroy(this.refreshVimSub);
    }
  }
}
