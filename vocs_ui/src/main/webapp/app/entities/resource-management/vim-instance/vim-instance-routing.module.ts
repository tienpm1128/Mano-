import { Injectable, NgModule } from '@angular/core';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { VimInstanceComponent } from './vim-instance.component';
import { UserRouteAccessService } from 'app/core';
import { VimInstanceDetailComponent } from './vim-instance-detail/vim-instance-detail.component';
import { LoginVimComponent } from 'app/entities/resource-management/vim-instance/login-vim/login-vim.component';
import { UserVimRouteAccessService } from 'app/core/auth/user-vim-route-access-service';
import { ChooseProjectComponent } from 'app/entities/resource-management/vim-instance/choose-project/choose-project.component';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { VimInstanceModel } from 'app/shared/model/vimInstance.model';
import { VimInstanceService } from 'app/entities/resource-management/vim-instance/vim-instance.service';
import { RoleComponent } from './role/role.component';
import { ServerGroupComponent } from './server-group/server-group.component';
import { UserVimComponent } from './user-vim/user-vim.component';
import { ImageComponent } from './image/image.component';
import { ProjectComponent } from './project/project.component';
import { NetworkComponent } from './network/network.component';
import { SoftwareComponent } from 'app/entities/resource-management/vim-instance/software/software.component';

@Injectable({ providedIn: 'root' })
export class VimInstancePublicResolve implements Resolve<VimInstanceModel> {
  constructor(private service: VimInstanceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<VimInstanceModel> {
    const id = route.params['vimInstanceId'] ? route.params['vimInstanceId'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((nsdDetail: HttpResponse<any>) => nsdDetail.body.data)
      );
    }
    return of(new VimInstanceModel());
  }
}

const routes: Routes = [
  {
    path: 'vims',
    data: {
      breadcrumb: 'mano.vimInstances.page-title',
      authorities: ['ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: [
      {
        path: '',
        component: VimInstanceComponent,
        data: {
          breadcrumb: false,
          pageTitle: 'mano.vimInstances.page-title'
        }
      },
      {
        path: ':vimInstanceId',
        data: {
          breadcrumb: true,
          pageTitle: 'mano.vimInstances.page-title'
        },
        canActivate: [UserVimRouteAccessService],
        children: [
          {
            path: '',
            component: VimInstanceDetailComponent,
            data: {
              breadcrumb: false,
              pageTitle: 'mano.vimInstances.page-title'
            },
            resolve: {
              vimInstance: VimInstancePublicResolve
            }
          },
          {
            path: 'role',
            component: RoleComponent,
            data: {
              breadcrumb: 'mano.role.breadcrumb',
              pageTitle: 'mano.vimInstances.page-title'
            }
          },
          {
            path: 'server-group',
            component: ServerGroupComponent,
            data: {
              breadcrumb: 'mano.servergroup.breadcrumb',
              pageTitle: 'mano.vimInstances.page-title'
            }
          },
          {
            path: 'user-vim',
            component: UserVimComponent,
            data: {
              breadcrumb: 'mano.userVim.breadcrumb',
              pageTitle: 'mano.vimInstances.page-title'
            }
          },
          {
            path: 'image',
            component: ImageComponent,
            data: {
              breadcrumb: 'mano.image.breadcrumb',
              pageTitle: 'mano.vimInstances.page-title'
            }
          },
          {
            path: 'project',
            component: ProjectComponent,
            data: {
              breadcrumb: 'mano.project.breadcrumb',
              pageTitle: 'mano.vimInstances.page-title'
            }
          },
          {
            path: 'network',
            component: NetworkComponent,
            data: {
              breadcrumb: 'mano.network.breadcrumb',
              pageTitle: 'mano.vimInstances.page-title'
            }
          },
          {
            path: 'login',
            component: LoginVimComponent,
            data: {
              breadcrumb: 'mano.vimInstances.login',
              pageTitle: 'mano.vimInstances.page-title'
            }
          },
          {
            path: 'user/:vimUserId/project',
            component: ChooseProjectComponent,
            data: {
              breadcrumb: 'mano.vinInstances.chooseProject',
              pageTitle: 'mano.vimInstances.page-title'
            }
          },
          {
            path: 'software',
            component: SoftwareComponent,
            data: {
              breadcrumb: 'mano.software.breadcrumb',
              authorities: ['PROVIDER_MEMBER'],
              pageTitle: 'mano.vimInstances.page-title'
            }
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
export class VimInstanceRoutingModule {}
