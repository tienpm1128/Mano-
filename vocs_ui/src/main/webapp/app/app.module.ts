import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { NgJhipsterModule, JhiEventManager } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { VOcsSharedModule } from 'app/shared';
import { VOcsCoreModule } from 'app/core';
import { VOcsAppRoutingModule } from './app-routing.module';
import { VOcsHomeModule } from './home/home.module';
import { VOcsEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, ActiveMenuDirective, ErrorComponent } from './layouts';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';
import { NgScrollbarModule } from 'ngx-scrollbar';
import { AutofocusDirective } from './autofocus.directive';
import { ConfirmDialogModule } from 'primeng/primeng';
import { ToastModule } from 'primeng/toast';
import 'chart.js';
declare var Chart;
Chart.pluginService.register({
  beforeDraw(chart) {
    if (chart.config.options.elements.center) {
      // Get ctx from string
      const ctx = chart.chart.ctx;

      // Get options from the center object in options
      const centerConfig = chart.config.options.elements.center;
      const fontStyle = centerConfig.fontStyle || 'Arial';
      const txt = centerConfig.text;
      const color = centerConfig.color || '#000';
      // const sidePadding = centerConfig.sidePadding || 20;
      // const sidePaddingCalculated = (sidePadding / 100) * (chart.innerRadius * 2);
      // Start with a base font of 30px
      ctx.font = '30px ' + fontStyle;

      // Get the width of the string and also the width of the element minus 10 to give it 5px side padding
      // const stringWidth = ctx.measureText(txt).width;
      // const elementWidth = chart.innerRadius * 2 - sidePaddingCalculated;

      // Find out how much the font can grow in width.
      // const widthRatio = elementWidth / stringWidth;
      // const newFontSize = Math.floor(30 * widthRatio);
      const elementHeight = chart.innerRadius * 0.9;

      // Pick a new font size so it will not be larger than the height of label.
      const fontSizeToUse = Math.max(24, elementHeight);
      // const fontSizeToUse = 36;

      // Set font settings to draw it correctly.
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      const centerX = (chart.chartArea.left + chart.chartArea.right) / 2;
      const centerY = (chart.chartArea.top + chart.chartArea.bottom) / 2;
      ctx.font = 'bold ' + fontSizeToUse + 'px ' + fontStyle;
      ctx.fillStyle = color;

      // Draw text in center
      ctx.fillText(txt, centerX, centerY);
    }
  }
});

@NgModule({
  imports: [
    BrowserModule,
    NgxWebstorageModule.forRoot({ prefix: 'mano', separator: '-' }),
    NgJhipsterModule.forRoot({
      // set below to true to make alerts look like toast
      alertAsToast: false,
      alertTimeout: 10000,
      i18nEnabled: true,
      defaultI18nLang: 'en'
    }),
    VOcsSharedModule.forRoot(),
    VOcsCoreModule,
    VOcsHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    VOcsEntityModule,
    VOcsAppRoutingModule,
    NgScrollbarModule,
    ConfirmDialogModule,
    ToastModule
  ],
  declarations: [
    JhiMainComponent,
    NavbarComponent,
    ErrorComponent,
    ActiveMenuDirective,
    FooterComponent,
    SidebarComponent,
    AutofocusDirective
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true
    },
    JhiEventManager
  ],
  bootstrap: [JhiMainComponent]
})
export class VOcsAppModule {
  constructor(private dpConfig: NgbDatepickerConfig) {
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
  }
}
