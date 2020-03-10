import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FmNotificationsComponent } from './fm-notifications.component';
import { UserRouteAccessService } from 'app/core';

const routes: Routes = [
  {
    path: 'nsfm/notification',
    component: FmNotificationsComponent,
    data: {
      breadcrumb: 'mano.fNotifications.title',
      authorities: ['PROVIDER_MEMBER'],
      pageTitle: 'mano.fNotifications.header'
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmNotificationsRoutingModule {}
