import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PmJobsRoutingModule } from './pm-jobs-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import {
  CheckboxModule,
  PaginatorModule,
  DialogModule,
  ConfirmDialogModule,
  KeyFilterModule,
  ConfirmationService,
  MessageService,
  ListboxModule,
  CalendarModule,
  InputTextModule
} from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { PmJobsComponent } from './pm-jobs.component';
import { DetailPmJobsComponent } from 'app/entities/performance/ns/pm-jobs/detail-pm-jobs/detail-pm-jobs.component';

@NgModule({
  declarations: [PmJobsComponent, DetailPmJobsComponent],
  imports: [
    CommonModule,
    PmJobsRoutingModule,
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
    CalendarModule,
    InputTextModule
  ],
  providers: [ConfirmationService, MessageService]
})
export class PmJobsModule {}
