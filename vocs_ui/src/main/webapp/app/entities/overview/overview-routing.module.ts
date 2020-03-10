import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OverviewComponent } from 'app/entities/overview/overview.component';
import { UserRouteAccessService } from 'app/core';

const routes: Routes = [
  {
    path: '',
    component: OverviewComponent,
    data: {
      breadcrumb: false,
      pageTitle: 'mano.overview.title',
      authorities: ['PROVIDER_ADMIN', 'PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [UserRouteAccessService]
})
export class OverviewRoutingModule {}
