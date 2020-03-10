import { FormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NsInstanceRoutingModule } from './ns-instance-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import { CheckboxModule, ConfirmDialogModule, DialogModule, MessageService, PaginatorModule, KeyFilterModule } from 'primeng/primeng';
import { NsInstanceComponent } from 'app/entities/network-service/ns-instance/ns-instance.component';
import { NsInstanceDetailComponent } from './ns-instance-detail/ns-instance-detail.component';
import { InputMaskModule } from 'primeng/inputmask';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';

@NgModule({
  declarations: [NsInstanceComponent, NsInstanceDetailComponent],
  imports: [
    CommonModule,
    NsInstanceRoutingModule,
    BrowserAnimationsModule,
    VOcsSharedModule,
    TableModule,
    CheckboxModule,
    PaginatorModule,
    DialogModule,
    ConfirmDialogModule,
    InputMaskModule,
    BreadcrumbModule,
    ToastModule,
    FormsModule,
    KeyFilterModule
  ],
  providers: [MessageService]
})
export class NsInstanceModule {}
