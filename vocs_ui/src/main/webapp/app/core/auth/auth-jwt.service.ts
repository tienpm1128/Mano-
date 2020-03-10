import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';

import { SERVER_API_URL } from 'app/app.constants';
import { MessageService } from 'primeng/api';

@Injectable({ providedIn: 'root' })
export class AuthServerProvider {
  constructor(
    private http: HttpClient,
    private $localStorage: LocalStorageService,
    private $sessionStorage: SessionStorageService,
    private messageService: MessageService
  ) {}

  getToken() {
    return this.$localStorage.retrieve('authenticationToken') || this.$sessionStorage.retrieve('authenticationToken');
  }

  login(credentials): Observable<any> {
    const data = {
      username: credentials.username,
      password: credentials.password,
      rememberMe: credentials.rememberMe
    };
    return this.http.post(SERVER_API_URL + '/users/auth', data, { observe: 'response' }).pipe(map(authenticateSuccess.bind(this)));

    function authenticateSuccess(resp) {
      const token = resp.body.data.access_token;
      resp.body.data.loggedTime = new Date().getTime();
      if (token && resp.body.data.token_type === 'Bearer') {
        const jwt = token;
        this.storeAuthenticationToken(data.username, resp.body.data, jwt, credentials.rememberMe);
        return jwt;
      }
    }
  }

  loginVim(credentials, vimID: string, vimNAME: string, isStorableVimUser: boolean): Observable<any> {
    const data = {
      username: credentials.username,
      password: credentials.password
    };
    return this.http
      .post(SERVER_API_URL + '/vim/v1/vim_instances/' + vimID + '/validate_vim_user', data, { observe: 'response' })
      .pipe(map(authenticateSuccess.bind(this)));

    function authenticateSuccess(resp) {
      if (resp.body.errorCode === '00') {
        const vimUser = {
          userId: resp.body.data,
          username: data.username,
          vimId: vimID,
          vimName: vimNAME
        };
        if (vimUser) {
          if (isStorableVimUser) {
            this.storeVimAuthentication(vimUser);
          }
          return vimUser;
        }
      } else {
        this.messageService.add({
          severity: 'error',
          summary: 'Login Vim',
          detail: resp.body.message,
          life: 10000
        });
        return resp;
      }
    }
  }

  loginWithToken(username, loggedUser, jwt, rememberMe) {
    if (jwt) {
      this.storeAuthenticationToken(username, loggedUser, jwt, rememberMe);
      return Promise.resolve(jwt);
    } else {
      return Promise.reject('auth-jwt-service Promise reject'); // Put appropriate error message here
    }
  }

  storeAuthenticationToken(username, loggedUser, jwt, rememberMe) {
    if (rememberMe) {
      loggedUser.username = username;
      this.$localStorage.store('authenticationToken', jwt);
      this.$localStorage.store('loggedUser', loggedUser);
    } else {
      this.$sessionStorage.store('authenticationToken', jwt);
      this.$sessionStorage.store('loggedUser', loggedUser);
    }
  }

  storeVimAuthentication(vimUser: object) {
    const vimStorage = this.$localStorage.retrieve('loggedVimUser');
    if (vimStorage) {
      vimStorage.push(vimUser);
      this.$localStorage.store('loggedVimUser', vimStorage);
    } else {
      this.$localStorage.store('loggedVimUser', [vimUser]);
    }
  }

  logout(): Observable<any> {
    return new Observable(observer => {
      this.$localStorage.clear('authenticationToken');
      this.$sessionStorage.clear('authenticationToken');
      observer.complete();
    });
  }
}
