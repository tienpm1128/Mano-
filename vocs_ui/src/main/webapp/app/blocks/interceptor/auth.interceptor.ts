import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  vimId: string;
  openStackUserId: string;
  constructor(private localStorage: LocalStorageService, private sessionStorage: SessionStorageService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!request || !request.url || (/^http/.test(request.url) && !(SERVER_API_URL && request.url.startsWith(SERVER_API_URL)))) {
      return next.handle(request);
    }
    const vimStorage = this.localStorage.retrieve('loggedvimuser');
    const vimIdRegex = /\/{vimId:[\d|\w]+}/g;
    const checkVimId = request.url.match(vimIdRegex);
    const opUserRegex = /\/{openStackUserId:[\w\d-]+}/g;
    const checkOpUser = request.url.match(opUserRegex);
    if (checkVimId && checkVimId.length > 0) {
      this.vimId = checkVimId[0].replace('/{vimId:', '').replace('}', '');
      request = request.clone({
        url: request.url.replace(vimIdRegex, '')
      });
    }
    if (checkOpUser && checkOpUser.length > 0) {
      this.openStackUserId = checkOpUser[0].replace('/{openStackUserId:', '').replace('}', '');
      request = request.clone({
        url: request.url.replace(opUserRegex, '')
      });
    }

    const token = this.localStorage.retrieve('authenticationToken') || this.sessionStorage.retrieve('authenticationToken');
    let opUser = null;
    if (vimStorage !== null) {
      vimStorage.forEach(vim => {
        if (vim.vimId === this.vimId) {
          opUser = vim;
        }
      });
    }
    if (!!token) {
      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + token
        }
      });
    }
    if (this.vimId) {
      request = request.clone({
        setHeaders: {
          vimId: this.vimId
        }
      });
    }
    if (this.openStackUserId) {
      request = request.clone({
        setHeaders: {
          openStackUserId: this.openStackUserId
        }
      });
    }
    if (opUser !== null) {
      if (request.url.indexOf('{openStackUserId}') !== -1) {
        request = request.clone({
          url: request.url.replace('{openStackUserId}', opUser.userId)
        });
      }
      if (opUser.userId) {
        request = request.clone({
          setHeaders: {
            openStackUserId: opUser.userId
          }
        });
      }
      if (opUser.projectId) {
        request = request.clone({
          setHeaders: {
            projectId: opUser.projectId
          }
        });
      }
    }
    return next.handle(request);
  }
}
