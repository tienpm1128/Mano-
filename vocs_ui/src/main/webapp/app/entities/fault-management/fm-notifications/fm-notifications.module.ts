import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FmNotificationsRoutingModule } from './fm-notifications-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import {
  CheckboxModule,
  PaginatorModule,
  DialogModule,
  ConfirmDialogModule,
  KeyFilterModule,
  MessageService,
  ConfirmationService
} from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { FmNotificationsComponent } from './fm-notifications.component';

@NgModule({
  declarations: [FmNotificationsComponent],
  imports: [
    CommonModule,
    FmNotificationsRoutingModule,
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
export class FmNotificationsModule {}
