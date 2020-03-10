import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VimNotificationsComponent } from './notifications.component';
import { UserRouteAccessService } from 'app/core';

const routes: Routes = [
  {
    path: 'hppm/notification',
    component: VimNotificationsComponent,
    data: {
      breadcrumb: 'mano.notifications.title',
      pageTitle: 'mano.notifications.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NotificationsRoutingModule {}
