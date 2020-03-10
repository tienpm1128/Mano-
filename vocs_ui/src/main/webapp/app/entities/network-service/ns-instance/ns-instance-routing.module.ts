import { NgModule, Injectable } from '@angular/core';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { NsInstanceComponent } from 'app/entities/network-service/ns-instance/ns-instance.component';
import { NsInstanceService } from './ns-instance.service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { NsInstanceDetailComponent } from './ns-instance-detail/ns-instance-detail.component';
import { NsInstance } from 'app/shared/model/nsInstance.model';

@Injectable({ providedIn: 'root' })
export class NsInstancePublicResolve implements Resolve<NsInstance> {
  constructor(private service: NsInstanceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<NsInstance> {
    const id = route.params['nsInstanceId'] ? route.params['nsInstanceId'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((nsdDetail: HttpResponse<any>) => nsdDetail.body.data)
      );
    }
    return of(new NsInstance());
  }
}

const routes: Routes = [
  {
    path: 'nslcm',
    data: {
      breadcrumb: 'mano.nsInstances.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: '',
        component: NsInstanceComponent,
        data: {
          breadcrumb: false,
          pageTitle: 'mano.nsInstances.title'
        },
        canActivate: [UserRouteAccessService]
      },
      {
        path: ':nsInstanceId',
        component: NsInstanceDetailComponent,
        data: {
          breadcrumb: true,
          pageTitle: 'mano.nsInstances.title'
        },
        canActivate: [UserRouteAccessService],
        resolve: {
          nsInstanceDetail: NsInstancePublicResolve
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NsInstanceRoutingModule {}
