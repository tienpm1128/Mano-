import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { NsdSubscriptionsComponent } from 'app/entities/network-service/nsd-subscriptions/nsd-subscriptions.component';

const routes: Routes = [
  {
    path: 'nsd-subscriptions',
    component: NsdSubscriptionsComponent,
    data: {
      breadcrumb: 'mano.nsdSubscriptions.title',
      authorities: ['PROVIDER_MEMBER'],
      pageTitle: 'mano.nsdSubscriptions.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NsdSubscriptionsRoutingModule {}
