import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SupportingServiceRoutingModule } from './supporting-service-routing.module';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { SupportingServiceComponent } from './supporting-service.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CheckboxModule, PaginatorModule, DialogModule, ConfirmDialogModule, KeyFilterModule } from 'primeng/primeng';
import { VOcsSharedModule } from 'app/shared';

@NgModule({
  declarations: [SupportingServiceComponent],
  imports: [
    CommonModule,
    SupportingServiceRoutingModule,
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
  ]
})
export class SupportingServiceModule {}
