import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { AccountService, LoginService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-login-vim',
  templateUrl: './login-vim.component.html',
  styleUrls: ['./login-vim.component.scss']
})
export class LoginVimComponent implements OnInit {
  authenticationError: boolean;
  vimId: string;
  vimName: string;
  loginForm = this.fb.group({
    username: [''],
    password: ['']
  });

  blockSpace: RegExp = /[^\s]/;

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private messageService: MessageService
  ) {
    this.vimId = this.activatedRoute.snapshot.params['vimInstanceId'];
    this.vimName = this.activatedRoute.snapshot.queryParams['vim'];
    if (this.accountService.getCurrentLoggedVimUser(this.vimId)) {
      this.router.navigate(['/vims', this.vimId, 'user', this.accountService.getCurrentLoggedVimUser(this.vimId).userId, 'project']);
    }
  }

  ngOnInit() {}

  loginVim() {
    this.loginService
      .loginVim(
        {
          username: this.loginForm.get('username').value,
          password: this.loginForm.get('password').value
        },
        this.vimId,
        this.vimName,
        true
      )
      .then((res: any) => {
        this.authenticationError = false;
        this.router.navigate(['/vims', this.vimId, 'user', res['userId'], 'project']);
      })
      .catch(() => {
        this.authenticationError = true;
      });
  }
}
