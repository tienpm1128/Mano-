import { Injectable, NgModule } from '@angular/core';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SubscriptionsComponent } from './subscriptions.component';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { NsPmSubscriptionsService } from 'app/entities/performance/ns/subscriptions/ns-pm-subscriptions.service';
import { NsSubscription } from 'app/shared/model/nsSubscription.model';
import { MonitorNsPmComponent } from 'app/entities/performance/ns/subscriptions/monitor-ns-pm/monitor-ns-pm.component';
import { MonitorNsThresholdComponent } from 'app/entities/performance/ns/subscriptions/monitor-ns-threshold/monitor-ns-threshold.component';

@Injectable({ providedIn: 'root' })
export class NspmSubscriptionResolve implements Resolve<NsSubscription> {
  constructor(private service: NsPmSubscriptionsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<NsSubscription> {
    const id = route.params['nspmSubscriptionId'] ? route.params['nspmSubscriptionId'] : null;
    if (id) {
      return this.service.detail(id).pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((nspmSubscriptionDetail: HttpResponse<any>) => nspmSubscriptionDetail.body.data)
      );
    }
    return of(new NsSubscription());
  }
}

const routes: Routes = [
  {
    path: 'nspm/subscription',
    data: {
      breadcrumb: 'mano.subscriptions.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: '',
        component: SubscriptionsComponent,
        data: {
          breadcrumb: false,
          pageTitle: 'mano.subscriptions.title'
        }
      },
      {
        path: ':nspmSubscriptionId/pmjob',
        component: MonitorNsPmComponent,
        data: {
          breadcrumb: 'Monitoring',
          pageTitle: 'mano.monitor.title'
        },
        resolve: {
          nspmSubscription: NspmSubscriptionResolve
        }
      },
      {
        path: ':nspmSubscriptionId/threshold',
        component: MonitorNsThresholdComponent,
        data: {
          breadcrumb: 'Monitoring',
          pageTitle: 'mano.monitor.title'
        },
        resolve: {
          nspmSubscription: NspmSubscriptionResolve
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SubscriptionsRoutingModule {}
