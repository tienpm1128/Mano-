import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NsdSubscriptionsRoutingModule } from './nsd-subscriptions-routing.module';
import { NsdSubscriptionsComponent } from 'app/entities/network-service/nsd-subscriptions/nsd-subscriptions.component';
import { TableModule } from 'primeng/table';
import { CheckboxModule, ConfirmationService, ConfirmDialogModule, MessageService, PaginatorModule } from 'primeng/primeng';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { ToastModule } from 'primeng/toast';

@NgModule({
  declarations: [NsdSubscriptionsComponent],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    VOcsSharedModule,
    NsdSubscriptionsRoutingModule,
    TableModule,
    CheckboxModule,
    PaginatorModule,
    ConfirmDialogModule,
    ToastModule,
    BreadcrumbModule
  ],
  providers: [ConfirmationService, MessageService]
})
export class NsdSubscriptionsModule {}
