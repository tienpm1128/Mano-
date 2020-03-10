import { Injectable, NgModule } from '@angular/core';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { NsdManagementComponent } from 'app/entities/network-service/nsd-management/nsd-management.component';
import { NsdDetailComponent } from 'app/entities/network-service/nsd-management/nsd-detail/nsd-detail.component';
import { INsd, Nsd } from 'app/shared/model/nsd.model';
import { NsdManagementService } from 'app/entities/network-service/nsd-management/nsd-management.service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { UserRouteAccessService } from 'app/core';
import { Vnfd } from 'app/shared/model/vnfd.model';
import { VnfdDetailComponent } from 'app/entities/network-service/nsd-management/nsd-detail/vnfd-detail/vnfd-detail.component';

@Injectable({ providedIn: 'root' })
export class NsdPublicResolve implements Resolve<Nsd> {
  constructor(private service: NsdManagementService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Nsd> {
    const id = route.params['nsdId'] ? route.params['nsdId'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((nsdDetail: HttpResponse<any>) => nsdDetail.body.data)
      );
    }
    return of(new Nsd());
  }
}

@Injectable({ providedIn: 'root' })
export class VnfdDetailPublicResolve implements Resolve<Vnfd> {
  constructor(private service: NsdManagementService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Vnfd> {
    const id = route.params['vnfdId'] ? route.params['vnfdId'] : null;
    if (id) {
      return this.service.findVnfd(id).pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((vnfdDetail: HttpResponse<any>) => vnfdDetail.body.data)
      );
    }
    return of(new Vnfd());
  }
}

const routes: Routes = [
  {
    path: 'nsd',
    data: {
      breadcrumb: 'mano.nsDescriptors.title',
      authorities: ['PROVIDER_MEMBER']
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: '',
        component: NsdManagementComponent,
        data: {
          breadcrumb: false,
          pageTitle: 'mano.nsDescriptors.title'
        }
      },
      {
        path: ':nsdId',
        data: {
          breadcrumb: true
        },
        resolve: {
          nsdDetail: NsdPublicResolve
        },
        children: [
          {
            path: '',
            component: NsdDetailComponent,
            data: {
              breadcrumb: false,
              pageTitle: 'mano.nsDescriptors.title'
            }
          },
          {
            path: 'vnfd',
            data: {
              breadcrumb: 'VNFD',
              pageTitle: 'mano.nsDescriptors.title'
            },
            children: [
              {
                path: '',
                redirectTo: '..',
                pathMatch: 'full',
                data: {
                  breadcrumb: false,
                  pageTitle: 'mano.nsDescriptors.title'
                }
              },
              {
                path: ':vnfdId',
                component: VnfdDetailComponent,
                data: {
                  breadcrumb: true,
                  pageTitle: 'mano.nsDescriptors.title'
                },
                resolve: {
                  vnfdDetail: VnfdDetailPublicResolve
                }
              }
            ]
          }
        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NsdManagementRoutingModule {}
