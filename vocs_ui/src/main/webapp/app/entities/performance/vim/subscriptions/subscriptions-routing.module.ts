import { Injectable, NgModule } from '@angular/core';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { VimSubscriptionsComponent } from './subscriptions.component';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { NsSubscription } from 'app/shared/model/nsSubscription.model';
import { MonitorHpPmComponent } from 'app/entities/performance/vim/subscriptions/monitor-hp-pm/monitor-hp-pm.component';
import { HpPmSubscriptionsService } from 'app/entities/performance/vim/subscriptions/hp-pm-subscriptions.service';

@Injectable({ providedIn: 'root' })
export class HppmSubscriptionResolve implements Resolve<NsSubscription> {
  constructor(private service: HpPmSubscriptionsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<NsSubscription> {
    const id = route.params['hppmSubscriptionId'] ? route.params['hppmSubscriptionId'] : null;
    if (id) {
      return this.service.detail(id).pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((hppmSubscriptionDetail: HttpResponse<any>) => hppmSubscriptionDetail.body.data)
      );
    }
    return of(new NsSubscription());
  }
}

const routes: Routes = [
  {
    path: 'hppm/subscription',
    data: {
      breadcrumb: 'mano.subscriptions.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: '',
        component: VimSubscriptionsComponent,
        data: {
          breadcrumb: false,
          pageTitle: 'mano.subscriptions.title'
        }
      },
      {
        path: ':hppmSubscriptionId',
        component: MonitorHpPmComponent,
        data: {
          breadcrumb: 'Monitoring',
          pageTitle: 'mano.monitor.title'
        },
        resolve: {
          hppmSubscription: HppmSubscriptionResolve
        }
      } /*,
      {
        path: ':hppmSubscriptionId/pmjob',
        component: MonitorHpPmComponent,
        data: {
          breadcrumb: 'Monitoring',
          pageTitle: 'mano.monitor.title'
        },
        resolve: {
          hppmSubscription: HppmSubscriptionResolve
        }
      },
      {
        path: ':hppmSubscriptionId/threshold',
        component: MonitorHpThresholdComponent,
        data: {
          breadcrumb: 'Monitoring',
          pageTitle: 'mano.monitor.title'
        },
        resolve: {
          hppmSubscription: HppmSubscriptionResolve
        }
      }*/
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SubscriptionsRoutingModule {}
