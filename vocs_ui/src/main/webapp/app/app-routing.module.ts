import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot([...LAYOUT_ROUTES], {
      enableTracing: DEBUG_INFO_ENABLED,
      scrollPositionRestoration: 'enabled'
    })
  ],
  exports: [RouterModule]
})
export class VOcsAppRoutingModule {}
