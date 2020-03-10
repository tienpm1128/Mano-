import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NsInstanceNotificationRoutingModule } from './ns-instance-notification-routing.module';
import { NsInstanceNotificationComponent } from 'app/entities/network-service/ns-instance-notification/ns-instance-notification.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import { CheckboxModule, PaginatorModule } from 'primeng/primeng';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';

@NgModule({
  declarations: [NsInstanceNotificationComponent],
  imports: [
    CommonModule,
    NsInstanceNotificationRoutingModule,
    BrowserAnimationsModule,
    VOcsSharedModule,
    TableModule,
    CheckboxModule,
    PaginatorModule,
    BreadcrumbModule
  ]
})
export class NsInstanceNotificationModule {}
