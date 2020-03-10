import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SupportingServiceComponent } from './supporting-service.component';
import { UserRouteAccessService } from 'app/core';

const routes: Routes = [
  {
    path: 'supporting-services',
    component: SupportingServiceComponent,
    data: {
      breadcrumb: 'mano.supportingService.title',
      pageTitle: 'mano.supportingService.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupportingServiceRoutingModule {}
