import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TenantRoutingModule } from './tenant-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import { CheckboxModule, PaginatorModule, DialogModule, ConfirmDialogModule, KeyFilterModule } from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { TenantComponent } from './tenant.component';

@NgModule({
  declarations: [TenantComponent],
  imports: [
    CommonModule,
    TenantRoutingModule,
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
export class TenantModule {}
