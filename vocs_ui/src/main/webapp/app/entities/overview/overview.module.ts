import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OverviewRoutingModule } from './overview-routing.module';
import { OverviewComponent } from 'app/entities/overview/overview.component';
import { VOcsSharedModule } from 'app/shared';
import { ChartModule } from 'primeng/chart';
import { TableModule } from 'primeng/table';
import { DropdownModule, MessageService, TooltipModule } from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';

@NgModule({
  declarations: [OverviewComponent],
  imports: [CommonModule, VOcsSharedModule, OverviewRoutingModule, ChartModule, TableModule, TooltipModule, ToastModule, DropdownModule],
  providers: [MessageService]
})
export class OverviewModule {}
