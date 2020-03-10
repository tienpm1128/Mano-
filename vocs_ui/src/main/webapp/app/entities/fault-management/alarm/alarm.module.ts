import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AlarmRoutingModule } from './alarm-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import {
  PaginatorModule,
  CheckboxModule,
  DialogModule,
  ConfirmDialogModule,
  KeyFilterModule,
  ConfirmationService,
  MessageService,
  TooltipModule,
  ChartModule,
  DropdownModule
} from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { AlarmComponent } from './alarm.component';
import { AlarmDetailComponent } from './alarm-detail/alarm-detail.component';
import { OverviewRoutingModule } from 'app/entities/overview/overview-routing.module';

@NgModule({
  declarations: [AlarmComponent, AlarmDetailComponent],
  imports: [
    VOcsSharedModule,
    CommonModule,
    AlarmRoutingModule,
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
    TooltipModule,
    OverviewRoutingModule,
    ChartModule,
    DropdownModule
  ],
  providers: [ConfirmationService, MessageService]
})
export class AlarmModule {}
