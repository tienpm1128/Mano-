import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NsdManagementRoutingModule } from './nsd-management-routing.module';
import { NsdManagementComponent } from 'app/entities/network-service/nsd-management/nsd-management.component';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import {
  CheckboxModule,
  ConfirmationService,
  ConfirmDialogModule,
  DialogModule,
  FileUploadModule,
  MessageService,
  PaginatorModule,
  RadioButtonModule,
  TabViewModule,
  TooltipModule
} from 'primeng/primeng';
import { NsdDetailComponent } from 'app/entities/network-service/nsd-management/nsd-detail/nsd-detail.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { HttpModule } from '@angular/http';
import { VnfdDetailComponent } from './nsd-detail/vnfd-detail/vnfd-detail.component';

@NgModule({
  declarations: [NsdManagementComponent, NsdDetailComponent, VnfdDetailComponent],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    VOcsSharedModule,
    NsdManagementRoutingModule,
    TableModule,
    CheckboxModule,
    PaginatorModule,
    DialogModule,
    FileUploadModule,
    ConfirmDialogModule,
    RadioButtonModule,
    TooltipModule,
    ToastModule,
    BreadcrumbModule,
    TabViewModule,
    HttpModule
  ],
  providers: [ConfirmationService, MessageService]
})
export class NsdManagementModule {}
