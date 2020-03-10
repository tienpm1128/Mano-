import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { ManoServiceComponent } from 'app/entities/service/mano-service/mano-service.component';

const routes: Routes = [
  {
    path: 'mano-services',
    component: ManoServiceComponent,
    data: {
      breadcrumb: 'mano.manoService.title',
      pageTitle: 'mano.manoService.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManoServiceRoutingModule {}
