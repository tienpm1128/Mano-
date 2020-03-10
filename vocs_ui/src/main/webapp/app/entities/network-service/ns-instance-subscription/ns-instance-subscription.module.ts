import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NsInstanceSubscriptionRoutingModule } from './ns-instance-subscription-routing.module';
import { NsInstanceSubscriptionComponent } from './ns-instance-subscription.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import { CheckboxModule, ConfirmDialogModule, MessageService, PaginatorModule } from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';

@NgModule({
  declarations: [NsInstanceSubscriptionComponent],
  imports: [
    CommonModule,
    NsInstanceSubscriptionRoutingModule,
    BrowserAnimationsModule,
    VOcsSharedModule,
    BreadcrumbModule,
    TableModule,
    CheckboxModule,
    PaginatorModule,
    ConfirmDialogModule,
    ToastModule
  ],
  providers: [MessageService]
})
export class NsInstanceSubscriptionModule {}
