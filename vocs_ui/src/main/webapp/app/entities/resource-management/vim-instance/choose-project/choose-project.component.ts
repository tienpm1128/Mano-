import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { AccountService, LoginService, StateStorageService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService } from 'app/entities/resource-management/vim-instance/project/project.service';
import { IProjects } from 'app/shared/model/projects.model';
import { MessageService } from 'primeng/api';
import { LocalStorageService } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';
import { VimInstanceService } from 'app/entities/resource-management/vim-instance/vim-instance.service';

@Component({
  selector: 'jhi-choose-project',
  templateUrl: './choose-project.component.html',
  styleUrls: ['./choose-project.component.scss']
})
export class ChooseProjectComponent implements OnInit {
  authenticationError: boolean;
  vimId: string;
  vimUserId: string;
  projects: IProjects[];
  selectedProject: IProjects = null;

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private projectService: ProjectService,
    private messageService: MessageService,
    private localStorageService: LocalStorageService,
    private eventManager: JhiEventManager,
    private stateStorageService: StateStorageService,
    private vimInstanceService: VimInstanceService
  ) {
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
    if (this.accountService.getCurrentLoggedVimUser(this.vimId)) {
      this.vimUserId = this.accountService.getCurrentLoggedVimUser(this.vimId).userId;
    } else if (!this.vimUserId) {
      this.router.navigate(['/vims', this.vimId, 'login']);
    }
  }

  ngOnInit() {
    this.projectService.findProjectsByVimUser(this.vimUserId, this.vimId).subscribe(
      res => {
        if (res.body.errorCode === '00') {
          this.projects = res.body.data;
          this.localStorageService.store('vimProjects', this.projects);
        } else {
          this.messageService.add({ severity: 'error', summary: 'Vim Instance', detail: res.body.message, life: 10000 });
        }
      },
      res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Vim Instance',
          detail: res.error.status + ' ' + res.error.message,
          life: 10000
        });
      }
    );
  }

  save() {
    const vimStorage = this.localStorageService.retrieve('loggedvimuser');
    const newLoggedVimUser = this.accountService.getCurrentLoggedVimUser(this.vimId);
    const index = vimStorage.indexOf(newLoggedVimUser);
    vimStorage[index].projectId = this.selectedProject.projectId;
    vimStorage[index].projectName = this.selectedProject.projectName;
    this.eventManager.broadcast({ name: 'loginVim' });
    this.localStorageService.store('loggedvimuser', vimStorage);
    if (!this.localStorageService.retrieve('hypervisors')) {
      this.vimInstanceService.getResource(this.vimId, this.selectedProject.projectId).subscribe(
        res => {
          if (res.errorCode === '00') {
            if (res.data.hypervisor) {
              this.localStorageService.store('hypervisors', res.data.hypervisor);
              this.redirect();
            }
          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'Vim Instance',
              detail: res.body.message,
              life: 10000
            });
          }
        },
        res => {
          this.messageService.add({
            severity: 'error',
            summary: 'Vim Instance',
            detail: res.status + ' ' + res.message,
            life: 10000
          });
        }
      );
    } else {
      this.redirect();
    }
  }

  redirect() {
    if (this.stateStorageService.getUrl()) {
      this.router.navigate([this.stateStorageService.getUrl()]);
      this.stateStorageService.storeUrl(null);
    } else {
      this.router.navigate(['/vims', this.vimId]);
    }
  }
}
