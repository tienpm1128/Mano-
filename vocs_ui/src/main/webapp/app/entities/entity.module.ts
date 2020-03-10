import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NsdSubscriptionsModule } from 'app/entities/network-service/nsd-subscriptions/nsd-subscriptions.module';
import { NsdManagementModule } from 'app/entities/network-service/nsd-management/nsd-management.module';
import { VimInstanceModule } from 'app/entities/resource-management/vim-instance/vim-instance.module';
import { UserManoModule } from 'app/entities/resource-management/user-mano/user-mano.module';
import { NsInstanceModule } from 'app/entities/network-service/ns-instance/ns-instance.module';
import { NsInstanceNotificationModule } from 'app/entities/network-service/ns-instance-notification/ns-instance-notification.module';
import { NsInstanceSubscriptionModule } from 'app/entities/network-service/ns-instance-subscription/ns-instance-subscription.module';
import { NsdNotificationsModule } from 'app/entities/network-service/nsd-notifications/nsd-notifications.module';
import { CommonModule } from '@angular/common';
import { VOcsSharedModule } from 'app/shared';
import { OverviewModule } from 'app/entities/overview/overview.module';
import { AlarmModule } from './fault-management/alarm/alarm.module';
import { FaultSubscriptionsModule } from './fault-management/fault-subscriptions/fault-subscriptions.module';
import { FmNotificationsModule } from './fault-management/fm-notifications/fm-notifications.module';
import { PmJobsModule } from './performance/ns/pm-jobs/pm-jobs.module';
import { ThresholdsModule } from './performance/ns/thresholds/thresholds.module';
import { NotificationsModule } from './performance/ns/notifications/notifications.module';
import { VimPmJobsModule } from './performance/vim/pm-jobs/pm-jobs.module';
import { VimThresholdsModule } from './performance/vim/thresholds/thresholds.module';
import { VimNotificationsModule } from './performance/vim/notifications/notifications.module';
import { SubscriptionsModule } from 'app/entities/performance/ns/subscriptions/subscriptions.module';
import { SupportingServiceModule } from './service/supporting-service/supporting-service.module';
import { VimSubscriptionsModule } from './performance/vim/subscriptions/subscriptions.module';
import { TenantModule } from './tenant/tenant.module';
import { ManoServiceComponent } from './service/mano-service/mano-service.component';
import { ManoServiceModule } from 'app/entities/service/mano-service/mano-service.module';

@NgModule({
  imports: [
    CommonModule,
    VOcsSharedModule,
    OverviewModule,
    NsInstanceModule,
    NsInstanceNotificationModule,
    NsInstanceSubscriptionModule,
    NsdManagementModule,
    NsdNotificationsModule,
    NsdSubscriptionsModule,
    UserManoModule,
    VimInstanceModule,
    AlarmModule,
    FaultSubscriptionsModule,
    FmNotificationsModule,
    PmJobsModule,
    VimPmJobsModule,
    ThresholdsModule,
    VimThresholdsModule,
    NotificationsModule,
    VimNotificationsModule,
    SubscriptionsModule,
    VimSubscriptionsModule,
    VimThresholdsModule,
    SupportingServiceModule,
    TenantModule,
    ManoServiceModule
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VOcsEntityModule {}
