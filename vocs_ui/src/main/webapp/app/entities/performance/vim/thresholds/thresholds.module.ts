import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ThresholdsRoutingModule } from './thresholds-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import { CheckboxModule, PaginatorModule, DialogModule, ConfirmDialogModule, KeyFilterModule, ListboxModule } from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { VimThresholdsComponent } from './thresholds.component';
import { DetailThresholdsComponent } from 'app/entities/performance/vim/thresholds/detail-thresholds/detail-thresholds.component';

@NgModule({
  declarations: [VimThresholdsComponent, DetailThresholdsComponent],
  imports: [
    CommonModule,
    ThresholdsRoutingModule,
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
    ListboxModule
  ]
})
export class VimThresholdsModule {}
