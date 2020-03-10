import { ConfirmationService, ConfirmDialog, MessageService } from 'primeng/primeng';
import { HostListener, NgZone, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';
import { NavigationEnd, Router } from '@angular/router';
import { AccountService, MyAccount } from 'app/core';
import { NotificationService } from 'app/shared/notification/notification.service';
import { Subscription } from 'rxjs';
import { LocalStorageService } from 'ngx-webstorage';
import { MessageQueue } from 'app/shared/notification/MessageQueue';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
  @ViewChild('sidebarIcon', { static: true }) sidebarIcon;
  @ViewChild('confirmDialog', { static: true }) confirmDialog: ConfirmDialog;

  loggedUser: MyAccount = new MyAccount();
  loggedVim: any;
  screenWidth: number;
  isAdmin = false;
  observerNotification: Subscription;
  noti: MessageQueue;
  notifications = [];
  specialToast = false;
  notifiIcon: number;

  constructor(
    private eventManager: JhiEventManager,
    private router: Router,
    private accountService: AccountService,
    private confirmationService: ConfirmationService,
    private notificationService: NotificationService,
    private messageService: MessageService,
    private ngZone: NgZone,
    private localStorageService: LocalStorageService
  ) {
    this.noti = new MessageQueue();
    this.getScreenSize();
  }

  @HostListener('window:resize', ['$event'])
  getScreenSize(event?) {
    this.screenWidth = window.innerWidth;
  }

  ngOnInit() {
    this.eventManager.subscribe('logout', () => this.doLogout());

    this.router.events.subscribe(val => {
      if (val instanceof NavigationEnd) {
        this.confirmDialog.hide();
        this.loggedUser = this.accountService.getCurrentLoggedUser();
      }
    });

    this.loggedUser = this.accountService.getCurrentLoggedUser();

    if (this.localStorageService.retrieve('loggedvimuser')) {
      this.loggedVim = this.localStorageService.retrieve('loggedvimuser')[0];
    }

    this.eventManager.subscribe('loginVim', res => {
      if (this.localStorageService.retrieve('loggedvimuser')) {
        this.loggedVim = this.localStorageService.retrieve('loggedvimuser')[0];
      }
    });

    this.eventManager.subscribe('logoutVim', res => {
      if (this.localStorageService.retrieve('loggedvimuser')) {
        this.loggedVim = this.localStorageService.retrieve('loggedvimuser')[0];
      }
    });

    this.eventManager.subscribe('showNav', res => {
      this.sidebarIcon.nativeElement.classList.add('is-active');
    });

    this.eventManager.subscribe('hideNav', res => {
      this.sidebarIcon.nativeElement.classList.remove('is-active');
    });

    this.isAdmin = this.accountService.hasAuthority('PROVIDER_ADMIN');
    this.notificationService.connect();

    this.observerNotification = this.eventManager.subscribe('notifications', res => {
      if (!res.hasOwnProperty('content')) {
        return;
      }

      this.notifications = this.localStorageService.retrieve('notifications');

      this.ngZone.run(() => {
        let notiHeader = 'Notification';
        let notiContent;
        const notiData = res.content;
        let notiType = 'success';
        switch (notiData.notificationType) {
          case 'PerformanceInformationAvailableNotification':
            notiContent = 'Performance information available notification';
            this.specialToast = false;
            this.notifiIcon = 0;
            break;
          case 'ThresholdCrossedNotification':
            notiHeader = 'Threshold Crossed Notification';
            if (notiData.crossingDirectionType) {
              notiHeader = 'Threshold Crossed Notification - ' + notiData.crossingDirectionType;
            }
            notiContent = `NS Instance ID: ${notiData.objectInstanceId ? notiData.objectInstanceId : ''}
              Ns Instance Name: ${notiData.objectInstanceName ? notiData.objectInstanceName : ''}
              Virtual Compute ID: ${notiData.subObjectInstanceId ? notiData.subObjectInstanceId : ''}
              Vnf Instance Name: ${notiData.subObjectInstanceName ? notiData.subObjectInstanceName : ''}
              Virtual Compute IP: ${notiData.subObjectInstanceIp ? notiData.subObjectInstanceIp : ''}
              Metric: ${notiData.performanceMetric ? notiData.performanceMetric : ''}
              Value: ${notiData.performanceValue ? notiData.performanceValue : ''}
              Time: ${notiData.timeStamp ? notiData.timeStamp : ''}`;
            notiType = 'alarm';
            this.specialToast = true;
            this.notifiIcon = 2;
            break;
          case 'ALARM_NOTIFICATION':
            notiHeader = 'Alarm - VNF_INSTANCE';
            notiContent = `ObjectInstanceId: ${notiData.alarm.objectInstanceId ? notiData.alarm.objectInstanceId : ''}
              SubObjectInstanceId: ${notiData.alarm.subObjectInstanceId ? notiData.alarm.subObjectInstanceId : ''}
              Service Name: ${notiData.alarm.subObjectInstanceName ? notiData.alarm.subObjectInstanceName : ''}
              Service IP: ${notiData.alarm.subObjectInstanceIp ? notiData.alarm.subObjectInstanceIp : ''}
              Type: ${notiData.alarm.managedObjectType ? notiData.alarm.managedObjectType : ''}
              Target Type: ${notiData.alarm.managedObjectTargetType ? notiData.alarm.managedObjectTargetType : ''}
              Enabled: ${notiData.alarm.enabled ? notiData.alarm.enabled : ''}
              Severity: ${notiData.alarm.perceivedSeverity ? notiData.alarm.perceivedSeverity : ''}
              Event Type: ${notiData.alarm.eventType ? notiData.alarm.eventType : ''}
              Event Time: ${notiData.alarm.eventTime ? notiData.alarm.eventTime : ''}`;
            notiType = 'alarm';
            this.specialToast = true;
            this.notifiIcon = 1;
            break;
          case 'VnfLcmOperationOccurrenceNotification':
            notiHeader = 'VNF Notification';
            if (notiData.operationState === 'FAILED') {
              notiContent = `Vnf Instance ID: ${notiData.vnfInstanceId ? notiData.vnfInstanceId : ''}
                Operation: ${notiData.operation ? notiData.operation : ''}
                Operation State: ${notiData.operationState ? notiData.operationState : ''}
                Time: ${notiData.timeStamp ? notiData.timeStamp : ''}
                Error: ${notiData.error.title ? notiData.error.title : ''} - ${notiData.error.status ? notiData.error.status : ''}
                ${notiData.error.detail ? notiData.error.detail : ''}`;
            } else {
              notiContent = `Vnf Instance ID: ${notiData.vnfInstanceId ? notiData.vnfInstanceId : ''}
                Operation: ${notiData.operation ? notiData.operation : ''}
                Operation State: ${notiData.operationState ? notiData.operationState : ''}
                Time: ${notiData.timeStamp ? notiData.timeStamp : ''}`;
            }
            notiData.operationState === 'FAILED' ? (notiType = 'vnfError') : (notiType = 'vnfSuccess');
            this.specialToast = true;
            this.notifiIcon = 0;
            break;
          default:
            notiContent = notiData.notificationType;
            this.specialToast = false;
            this.notifiIcon = 0;
            break;
        }
        this.messageService.add({ severity: notiType, summary: notiHeader, detail: notiContent, life: 10000 });
      });
    });
  }

  toggleNav(event: any) {
    event.target.classList.contains('is-active') ? event.target.classList.remove('is-active') : event.target.classList.add('is-active');
    this.eventManager.broadcast({ name: 'toggleNav' });
  }

  logout() {
    this.confirmationService.confirm({
      header: 'Confirm Logout',
      accept: () => {
        this.eventManager.broadcast({ name: 'logout' });
        this.doLogout();
        this.router.navigateByUrl('/login');
      }
    });
  }

  doLogout() {
    this.sidebarIcon.nativeElement.classList.add('is-active');
    this.accountService.logout();
    this.notificationService.disconnect();
    this.eventManager.destroy(this.observerNotification);
  }

  logoutVim() {
    this.confirmationService.confirm({
      header: 'Confirm Logout',
      accept: () => {
        this.accountService.logoutVim(this.loggedVim.vimId);
        if (this.localStorageService.retrieve('loggedvimuser').length > 0) {
          this.loggedVim = this.localStorageService.retrieve('loggedvimuser')[0];
        } else {
          this.loggedVim = null;
        }
      }
    });
  }

  decreaseNumofNotifications() {
    this.notificationService.decreateNumOfNotifications();
  }

  clearNotification() {
    this.localStorageService.clear('notifications');
    this.notifications = [];
  }
}
