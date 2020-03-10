import { Component, HostListener, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  @ViewChild('sidebar', { static: true }) sidebar;
  @ViewChild('sidebarContainer', { static: true }) sidebarContainer;
  @ViewChildren('navItemContainer') navItemContainer;

  currentRoute = '';
  screenWidth: number;

  constructor(private router: Router, private eventManager: JhiEventManager) {
    router.events.subscribe(val => {
      if (val instanceof NavigationEnd) {
        this.currentRoute = this.router.url;

        if (this.screenWidth <= 1024) {
          this.eventManager.broadcast({ name: 'hideNav' });
          this.sidebar.nativeElement.scrollTop = 0;
          this.sidebar.nativeElement.classList.remove('show-sidebar');
        }
      }
    });
    this.getScreenSize();
  }

  @HostListener('window:resize')
  getScreenSize() {
    this.screenWidth = window.innerWidth;
  }

  scroll() {
    this.sidebarContainer.scrollTop = 0;
  }

  ngOnInit() {
    this.eventManager.subscribe('toggleNav', res => {
      if (this.sidebar.nativeElement.classList.contains('show-sidebar')) {
        this.sidebar.nativeElement.scrollTop = 0;
        this.sidebar.nativeElement.classList.remove('show-sidebar');
      } else {
        this.sidebar.nativeElement.classList.add('show-sidebar');
      }
    });
    this.eventManager.subscribe('hideNav', res => {
      this.sidebar.nativeElement.scrollTop = 0;
      this.sidebar.nativeElement.classList.remove('show-sidebar');
    });
    this.eventManager.subscribe('logout', res => {
      this.sidebar.nativeElement.classList.add('show-sidebar');
    });
  }

  checkRoute(pattern: string) {
    if (this.currentRoute) {
      return this.currentRoute.startsWith(pattern);
    }
    return false;
  }

  setActive(event: any) {
    event.target.classList.contains('expanded') ? event.target.classList.remove('expanded') : event.target.classList.add('expanded');
  }

  onNavItemContainerClick() {
    this.sidebar.nativeElement.scroll(0, 0);
    this.eventManager.broadcast({ name: 'showNav' });
    this.sidebar.nativeElement.classList.add('show-sidebar');
  }
}
