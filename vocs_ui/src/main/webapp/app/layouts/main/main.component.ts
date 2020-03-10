import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';

import { JhiLanguageHelper } from 'app/core';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
  @ViewChild('siteContainer', { static: true }) siteContainer;
  @ViewChild('overlay', { static: true }) overlay;

  isLoginPage = false;
  screenWidth: number;

  constructor(private jhiLanguageHelper: JhiLanguageHelper, private router: Router, private eventManager: JhiEventManager) {
    router.events.subscribe(val => {
      if (val instanceof NavigationEnd) {
        this.isLoginPage = /^\/login\/?$/.test(this.router.url);
      }
    });
    this.getScreenSize();
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'vOcsApp';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  @HostListener('window:resize', ['$event'])
  getScreenSize(event?) {
    this.screenWidth = window.innerWidth;
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });

    this.eventManager.subscribe('toggleNav', res => {
      if (this.siteContainer.nativeElement.classList.contains('show-sidebar')) {
        this.siteContainer.nativeElement.classList.remove('show-sidebar');
        this.overlay.nativeElement.classList.remove('show');
      } else {
        this.siteContainer.nativeElement.classList.add('show-sidebar');
        this.overlay.nativeElement.classList.add('show');
      }
    });

    this.eventManager.subscribe('showNav', res => {
      this.siteContainer.nativeElement.classList.add('show-sidebar');
      this.overlay.nativeElement.classList.add('show');
    });

    this.eventManager.subscribe('hideNav', res => {
      this.siteContainer.nativeElement.classList.remove('show-sidebar');
      this.overlay.nativeElement.classList.remove('show');
    });

    this.eventManager.subscribe('logout', res => {
      this.siteContainer.nativeElement.classList.add('show-sidebar');
      this.overlay.nativeElement.classList.remove('show');
    });
  }

  hideSidebar() {
    this.eventManager.broadcast({ name: 'hideNav' });
  }
}
