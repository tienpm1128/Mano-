import { NgModule, Injectable } from '@angular/core';
import { Routes, RouterModule, Resolve, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';
import { FaultSubscriptionsComponent } from './fault-subscriptions.component';
import { UserRouteAccessService } from 'app/core';
import { DetailFaultSubscriptionsComponent } from './detail-fault-subscriptions/detail-fault-subscriptions.component';
import { FaultSubscription } from 'app/shared/model/faultSubscriptions.model';
import { FaultSubscriptionsService } from './fault-subscriptions.service';
import { Observable, of } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class FaultSubscriptionPublicResolve implements Resolve<FaultSubscription> {
  constructor(private service: FaultSubscriptionsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<FaultSubscription> {
    const id = route.params['faultSubscriptionsId'] ? route.params['faultSubscriptionsId'] : null;
    if (id) {
      return this.service.detail(id).pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((faultSubscriptionsDetail: HttpResponse<any>) => faultSubscriptionsDetail.body.data)
      );
    }
    return of(new FaultSubscription());
  }
}

const routes: Routes = [
  {
    path: 'nsfm/subscription',
    data: {
      breadcrumb: 'mano.faultSubscriptions.title',
      authorities: ['ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: '',
        component: FaultSubscriptionsComponent,
        data: {
          breadcrumb: '',
          pageTitle: 'mano.faultSubscriptions.title'
        }
      },
      {
        path: ':faultSubscriptionsId',
        component: DetailFaultSubscriptionsComponent,
        data: {
          breadcrumb: true,
          pageTitle: 'mano.faultSubscriptions.title'
        },
        resolve: {
          faultSubscriptionsDetail: FaultSubscriptionPublicResolve
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FaultSubscriptionsRoutingModule {}
