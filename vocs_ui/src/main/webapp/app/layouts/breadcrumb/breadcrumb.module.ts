import { BreadcrumbComponent } from './breadcrumb.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { VOcsSharedModule } from 'app/shared';

@NgModule({
    imports: [BrowserModule, RouterModule, VOcsSharedModule],
    declarations: [BreadcrumbComponent],
    exports: [BreadcrumbComponent]
})
export class BreadcrumbModule {}
