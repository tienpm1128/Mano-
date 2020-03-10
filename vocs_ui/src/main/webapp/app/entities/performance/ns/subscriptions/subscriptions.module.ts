import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SubscriptionsRoutingModule } from './subscriptions-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import {
  CheckboxModule,
  PaginatorModule,
  DialogModule,
  ConfirmDialogModule,
  KeyFilterModule,
  ListboxModule,
  ChartModule
} from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { SubscriptionsComponent } from './subscriptions.component';
import { MonitorNsPmComponent } from './monitor-ns-pm/monitor-ns-pm.component';
import { MonitorNsThresholdComponent } from './monitor-ns-threshold/monitor-ns-threshold.component';

@NgModule({
  declarations: [SubscriptionsComponent, MonitorNsPmComponent, MonitorNsThresholdComponent],
  imports: [
    CommonModule,
    SubscriptionsRoutingModule,
    BrowserAnimationsModule,
    VOcsSharedModule,
    TableModule,
    CheckboxModule,
    PaginatorModule,
    DialogModule,
    ToastModule,
    ConfirmDialogModule,
    BreadcrumbModule,
    KeyFilterModule,
    ListboxModule,
    ChartModule
  ]
})
export class SubscriptionsModule {}
