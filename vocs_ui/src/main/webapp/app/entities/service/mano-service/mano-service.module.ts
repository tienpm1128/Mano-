import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManoServiceRoutingModule } from './mano-service-routing.module';
import { VOcsSharedModule } from 'app/shared';
import { ManoServiceComponent } from 'app/entities/service/mano-service/mano-service.component';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/primeng';

@NgModule({
  declarations: [ManoServiceComponent],
  imports: [CommonModule, VOcsSharedModule, ManoServiceRoutingModule, BreadcrumbModule, TableModule, DropdownModule]
})
export class ManoServiceModule {}
