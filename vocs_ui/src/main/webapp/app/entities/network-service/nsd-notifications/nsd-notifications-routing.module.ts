import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { NsdNotificationsComponent } from 'app/entities/network-service/nsd-notifications/nsd-notifications.component';

const routes: Routes = [
  {
    path: 'nsd-notifications',
    component: NsdNotificationsComponent,
    data: {
      breadcrumb: 'mano.nsdNotifications.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NsdNotificationsRoutingModule {}
