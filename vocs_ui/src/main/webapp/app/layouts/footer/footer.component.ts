import { Component, OnInit, ViewChild } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html'
})
export class FooterComponent implements OnInit {
  @ViewChild('footer', { static: true }) footer;

  constructor(private eventManager: JhiEventManager) {}

  ngOnInit(): void {
    this.eventManager.subscribe('toggleNav', res => {
      this.footer.nativeElement.classList.contains('show-sidebar')
        ? this.footer.nativeElement.classList.remove('show-sidebar')
        : this.footer.nativeElement.classList.add('show-sidebar');
    });

    this.eventManager.subscribe('logout', res => {
      this.footer.nativeElement.classList.add('show-sidebar');
    });
  }
}
