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
import { VimSubscriptionsComponent } from './subscriptions.component';
import { MonitorHpPmComponent } from 'app/entities/performance/vim/subscriptions/monitor-hp-pm/monitor-hp-pm.component';
import { MonitorHpThresholdComponent } from 'app/entities/performance/vim/subscriptions/monitor-hp-threshold/monitor-hp-threshold.component';

@NgModule({
  declarations: [VimSubscriptionsComponent, MonitorHpPmComponent, MonitorHpThresholdComponent],
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
export class VimSubscriptionsModule {}
