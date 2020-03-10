import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserManoRoutingModule } from './user-mano-routing.module';
import { UserManoComponent } from './user-mano.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import {
  CheckboxModule,
  PaginatorModule,
  ConfirmDialogModule,
  DialogModule,
  ConfirmationService,
  MessageService,
  KeyFilterModule
} from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';

@NgModule({
  declarations: [UserManoComponent],
  imports: [
    CommonModule,
    UserManoRoutingModule,
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
export class UserManoModule {}
