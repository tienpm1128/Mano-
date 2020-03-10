import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { NsInstanceSubscriptionComponent } from 'app/entities/network-service/ns-instance-subscription/ns-instance-subscription.component';

const routes: Routes = [
  {
    path: 'ns-instance-subscriptions',
    component: NsInstanceSubscriptionComponent,
    data: {
      breadcrumb: 'mano.nsInstanceSubscriptions.title',
      pageTitle: 'mano.nsInstanceSubscriptions.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NsInstanceSubscriptionRoutingModule {}
