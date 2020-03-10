import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NsdNotificationsRoutingModule } from './nsd-notifications-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { NsdNotificationsComponent } from 'app/entities/network-service/nsd-notifications/nsd-notifications.component';
import { TableModule } from 'primeng/table';
import { CheckboxModule, PaginatorModule } from 'primeng/primeng';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';

@NgModule({
  declarations: [NsdNotificationsComponent],
  imports: [
    CommonModule,
    NsdNotificationsRoutingModule,
    BrowserAnimationsModule,
    VOcsSharedModule,
    TableModule,
    CheckboxModule,
    PaginatorModule,
    BreadcrumbModule
  ]
})
export class NsdNotificationsModule {}
