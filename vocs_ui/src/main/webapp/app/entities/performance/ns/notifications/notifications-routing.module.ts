import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotificationsComponent } from './notifications.component';
import { UserRouteAccessService } from 'app/core';

const routes: Routes = [
  {
    path: 'nspm/notification',
    component: NotificationsComponent,
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
