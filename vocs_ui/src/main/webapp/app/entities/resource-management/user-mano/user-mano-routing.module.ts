import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserManoComponent } from './user-mano.component';
import { UserRouteAccessService } from 'app/core';

const routes: Routes = [
  {
    path: 'user-mano',
    component: UserManoComponent,
    data: {
      breadcrumb: 'mano.userMano.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserManoRoutingModule {}
