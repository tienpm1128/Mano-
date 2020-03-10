import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { NsInstanceNotificationComponent } from 'app/entities/network-service/ns-instance-notification/ns-instance-notification.component';

const routes: Routes = [
  {
    path: 'ns-instance-notifications',
    component: NsInstanceNotificationComponent,
    data: {
      breadcrumb: 'mano.nsInstanceNotifications.title',
      authorities: ['PROVIDER_MEMBER'],
      pageTitle: 'mano.nsInstanceNotifications.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NsInstanceNotificationRoutingModule {}
