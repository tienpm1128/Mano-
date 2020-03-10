import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NotificationsRoutingModule } from './notifications-routing.module';
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
  MessageService
} from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { VimNotificationsComponent } from './notifications.component';

@NgModule({
  declarations: [VimNotificationsComponent],
  imports: [
    CommonModule,
    NotificationsRoutingModule,
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
export class VimNotificationsModule {}
