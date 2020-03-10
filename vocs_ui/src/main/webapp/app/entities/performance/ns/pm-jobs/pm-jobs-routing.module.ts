import { NgModule, Injectable } from '@angular/core';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { PmJobsComponent } from './pm-jobs.component';
import { PmJobs } from 'app/shared/model/pmjobs.model';
import { PmJobsService } from './pm-jobs.service';
import { Observable, of } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { map, filter } from 'rxjs/operators';
import { DetailPmJobsComponent } from 'app/entities/performance/ns/pm-jobs/detail-pm-jobs/detail-pm-jobs.component';

@Injectable({ providedIn: 'root' })
export class PmJobsPublicResolve implements Resolve<PmJobs> {
  constructor(private service: PmJobsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PmJobs> {
    const id = route.params['pmJobId'] ? route.params['pmJobId'] : null;
    if (id) {
      return this.service.detail(id).pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((pmJobsDetail: HttpResponse<any>) => pmJobsDetail.body.data)
      );
    }
    return of(new PmJobs());
  }
}

const routes: Routes = [
  {
    path: 'nspm/pmjobs',
    data: {
      breadcrumb: 'mano.pmJobs.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: '',
        component: PmJobsComponent,
        data: {
          breadcrumb: false,
          pageTitle: 'mano.pmJobs.title'
        }
      },
      {
        path: ':pmJobId',
        component: DetailPmJobsComponent,
        data: {
          breadcrumb: true,
          pageTitle: 'mano.pmJobs.title'
        },
        resolve: {
          pmJobsDetail: PmJobsPublicResolve
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PmJobsRoutingModule {}
