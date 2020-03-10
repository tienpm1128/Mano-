import { NgModule, Injectable } from '@angular/core';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { VimThresholdsComponent } from './thresholds.component';
import { ThresholdsService } from './thresholds.service';
import { Observable, of } from 'rxjs';
import { Threshold } from 'app/shared/model/threshold.model';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { DetailThresholdsComponent } from 'app/entities/performance/vim/thresholds/detail-thresholds/detail-thresholds.component';

@Injectable({ providedIn: 'root' })
export class ThresholdsPublicResolve implements Resolve<Threshold> {
  constructor(private service: ThresholdsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Threshold> {
    const id = route.params['thresholdId'] ? route.params['thresholdId'] : null;
    if (id) {
      return this.service.detail(id).pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((thresholdsDetail: HttpResponse<any>) => thresholdsDetail.body.data)
      );
    }
    return of(new Threshold());
  }
}

const routes: Routes = [
  {
    path: 'hppm/thresholds',
    data: {
      breadcrumb: 'mano.thresholds.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: '',
        component: VimThresholdsComponent,
        data: {
          breadcrumb: false,
          pageTitle: 'mano.thresholds.title'
        }
      },
      {
        path: ':thresholdId',
        component: DetailThresholdsComponent,
        data: {
          breadcrumb: true,
          pageTitle: 'mano.thresholds.title'
        },
        resolve: {
          thresholdsDetail: ThresholdsPublicResolve
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ThresholdsRoutingModule {}
