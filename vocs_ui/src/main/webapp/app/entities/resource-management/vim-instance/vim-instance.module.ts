import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VimInstanceComponent } from './vim-instance.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VOcsSharedModule } from 'app/shared';
import { TableModule } from 'primeng/table';
import {
  CheckboxModule,
  PaginatorModule,
  DialogModule,
  ConfirmDialogModule,
  ConfirmationService,
  ChartModule,
  TabViewModule,
  MessageService,
  PickListModule,
  TooltipModule,
  InputSwitchModule,
  OrderListModule,
  DropdownModule,
  KeyFilterModule,
  ChipsModule
} from 'primeng/primeng';
import { VimInstanceRoutingModule } from './vim-instance-routing.module';
import { VimInstanceDetailComponent } from './vim-instance-detail/vim-instance-detail.component';
import { ToastModule } from 'primeng/toast';
import { NgScrollbarModule, SmoothScrollModule } from 'ngx-scrollbar';
import { BreadcrumbModule } from 'app/layouts/breadcrumb/breadcrumb.module';
import { ChooseProjectComponent } from 'app/entities/resource-management/vim-instance/choose-project/choose-project.component';
import { LoginVimComponent } from 'app/entities/resource-management/vim-instance/login-vim/login-vim.component';
import { RoleComponent } from './role/role.component';
import { ServerGroupComponent } from './server-group/server-group.component';
import { UserVimComponent } from './user-vim/user-vim.component';
import { ImageComponent } from './image/image.component';
import { ProjectComponent } from './project/project.component';
import { NetworkComponent } from './network/network.component';
import { MaxValueValidator } from './image/max-value-validator.directive';
import { MinValueValidator } from './image/min-value-validator.directive';
import { SoftwareComponent } from 'app/entities/resource-management/vim-instance/software/software.component';

@NgModule({
  declarations: [
    VimInstanceComponent,
    VimInstanceDetailComponent,
    LoginVimComponent,
    ChooseProjectComponent,
    RoleComponent,
    ServerGroupComponent,
    UserVimComponent,
    ImageComponent,
    ProjectComponent,
    NetworkComponent,
    SoftwareComponent,
    MinValueValidator,
    MaxValueValidator
  ],
  imports: [
    CommonModule,
    VimInstanceRoutingModule,
    BrowserAnimationsModule,
    VOcsSharedModule,
    BreadcrumbModule,
    TableModule,
    CheckboxModule,
    PaginatorModule,
    DialogModule,
    ToastModule,
    ConfirmDialogModule,
    NgScrollbarModule,
    SmoothScrollModule,
    ChartModule,
    TabViewModule,
    TooltipModule,
    OrderListModule,
    InputSwitchModule,
    PickListModule,
    DropdownModule,
    KeyFilterModule,
    ChipsModule
  ],
  providers: [ConfirmationService, MessageService]
})
export class VimInstanceModule {}
