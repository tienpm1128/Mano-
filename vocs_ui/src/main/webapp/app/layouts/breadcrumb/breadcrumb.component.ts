import { Component } from '@angular/core';
import { ActivatedRoute, NavigationEnd, PRIMARY_OUTLET, Router } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.scss']
})
export class BreadcrumbComponent {
  breadcrumbs: Breadcrumb[];

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
    const breadcrumb: Breadcrumb = {
      label: 'global.menu.home',
      url: '/'
    };
    this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(event => {
      const root: ActivatedRoute = this.activatedRoute.root;
      this.breadcrumbs = this.getBreadcrumbs(root);
      this.breadcrumbs = [breadcrumb, ...this.breadcrumbs];
    });
  }

  private getBreadcrumbs(route: ActivatedRoute, uri = '', breadcrumbs: Breadcrumb[] = []): Breadcrumb[] {
    const ROUTE_DATA_BREADCRUMB = 'breadcrumb';
    const children: ActivatedRoute[] = route.children;
    if (children.length === 0) {
      return breadcrumbs;
    }
    for (const child of children) {
      if (child.outlet !== PRIMARY_OUTLET) {
        continue;
      }
      if (!child.snapshot.data.hasOwnProperty(ROUTE_DATA_BREADCRUMB)) {
        return this.getBreadcrumbs(child, uri, breadcrumbs);
      }
      if (child.snapshot.data[ROUTE_DATA_BREADCRUMB]) {
        const routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');
        uri += `/${routeURL}`;
        const breadcrumb: Breadcrumb = {
          label: child.snapshot.data[ROUTE_DATA_BREADCRUMB],
          url: uri
        };
        if (child.snapshot.data[ROUTE_DATA_BREADCRUMB] === true) {
          if (child.snapshot.params['nsdId']) {
            breadcrumb.label = child.snapshot.params['nsdId'];
          }
          if (child.snapshot.params['vnfdId']) {
            breadcrumb.label = child.snapshot.params['vnfdId'];
          }
          if (child.snapshot.params['nsInstanceId']) {
            breadcrumb.label = child.snapshot.params['nsInstanceId'];
          }
          if (child.snapshot.params['vimInstanceId']) {
            breadcrumb.label = child.snapshot.params['vimInstanceId'];
          }
          if (child.snapshot.params['pmJobId']) {
            breadcrumb.label = child.snapshot.params['pmJobId'];
          }
          if (child.snapshot.params['thresholdId']) {
            breadcrumb.label = child.snapshot.params['thresholdId'];
          }
          if (child.snapshot.params['alarmId']) {
            breadcrumb.label = child.snapshot.params['alarmId'];
          }
          if (child.snapshot.params['faultSubscriptionsId']) {
            breadcrumb.label = child.snapshot.params['faultSubscriptionsId'];
          }
        }
        breadcrumbs.push(breadcrumb);
      }
      return this.getBreadcrumbs(child, uri, breadcrumbs);
    }
    return breadcrumbs;
  }

  verifyBreadcrumb(translation: string) {
    return /^mano\.|^global\./.test(translation);
  }
}

export interface Breadcrumb {
  label: string;
  url: string;
}
