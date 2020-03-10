import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VOcsSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { KeyFilterModule } from 'primeng/primeng';

@NgModule({
  imports: [VOcsSharedModule, RouterModule.forChild([HOME_ROUTE]), BreadcrumbModule, KeyFilterModule],
  declarations: [HomeComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VOcsHomeModule {}
