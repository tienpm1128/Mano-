import { NgModule, Injectable } from '@angular/core';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AlarmComponent } from './alarm.component';
import { UserRouteAccessService } from 'app/core';
import { AlarmDetailComponent } from './alarm-detail/alarm-detail.component';
import { Alarm } from 'app/shared/model/alarm.model';
import { AlarmService } from './alarm.service';
import { Observable, of } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AlarmPublicResolve implements Resolve<Alarm> {
  constructor(private service: AlarmService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Alarm> {
    const id = route.params['alarmId'] ? route.params['alarmId'] : null;
    if (id) {
      return this.service.detail(id).pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((alarmDetail: HttpResponse<any>) => alarmDetail.body.data)
      );
    }
    return of(new Alarm());
  }
}

const routes: Routes = [
  {
    path: 'nsfm/alarm',
    data: {
      breadcrumb: 'mano.alarms.title',
      authorities: ['ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: '',
        component: AlarmComponent,
        data: {
          breadcrumb: false,
          pageTitle: 'mano.alarms.title'
        }
      },
      {
        path: ':alarmId',
        component: AlarmDetailComponent,
        data: {
          breadcrumb: true,
          pageTitle: 'mano.alarms.title'
        },
        resolve: {
          alarmDetail: AlarmPublicResolve
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AlarmRoutingModule {}
