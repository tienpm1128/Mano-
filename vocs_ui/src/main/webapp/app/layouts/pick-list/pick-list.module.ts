import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PickListComponent } from 'app/layouts/pick-list/pick-list.component';
import { VOcsSharedModule } from 'app/shared';
import { BrowserModule } from '@angular/platform-browser';
import { MessageService } from 'primeng/api';
import { OrderListModule } from 'primeng/primeng';

@NgModule({
  declarations: [PickListComponent],
  imports: [BrowserModule, CommonModule, VOcsSharedModule, OrderListModule],
  providers: [MessageService],
  exports: [PickListComponent]
})
export class PickListModule {}
