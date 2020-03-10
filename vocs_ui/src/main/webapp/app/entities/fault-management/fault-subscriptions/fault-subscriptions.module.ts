import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FaultSubscriptionsRoutingModule } from './fault-subscriptions-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import {
  CheckboxModule,
  PaginatorModule,
  DialogModule,
  ConfirmDialogModule,
  KeyFilterModule,
  ConfirmationService,
  MessageService
} from 'primeng/primeng';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { FaultSubscriptionsComponent } from './fault-subscriptions.component';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { DetailFaultSubscriptionsComponent } from './detail-fault-subscriptions/detail-fault-subscriptions.component';

@NgModule({
  declarations: [FaultSubscriptionsComponent, DetailFaultSubscriptionsComponent],
  imports: [
    CommonModule,
    FaultSubscriptionsRoutingModule,
    BrowserAnimationsModule,
    VOcsSharedModule,
    TableModule,
    CheckboxModule,
    PaginatorModule,
    DialogModule,
    ToastModule,
    ConfirmDialogModule,
    BreadcrumbModule,
    KeyFilterModule
  ],
  providers: [ConfirmationService, MessageService]
})
export class FaultSubscriptionsModule {}
