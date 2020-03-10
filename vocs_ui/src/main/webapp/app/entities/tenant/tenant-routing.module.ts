import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TenantComponent } from './tenant.component';
import { UserRouteAccessService } from 'app/core';

const routes: Routes = [
  {
    path: 'tenants',
    component: TenantComponent,
    data: {
      breadcrumb: 'mano.tenant.title',
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'mano.tenant.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TenantRoutingModule {}
