import { Injectable } from '@angular/core';
import { JhiEventManager, JhiLanguageService } from 'ng-jhipster';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of, Subject } from 'rxjs';

import { SERVER_API_URL, VIM_CONNECTION_INFO_KEY } from 'app/app.constants';
import { Account, MyAccount } from 'app/core/user/account.model';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private userIdentity: any;
  private authenticated = false;
  private authenticationState = new Subject<any>();

  constructor(
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService,
    private localStorage: LocalStorageService,
    private http: HttpClient,
    private eventManager: JhiEventManager
  ) {}

  getCurrentLoggedUser(): MyAccount {
    return this.localStorage.retrieve('loggeduser') || this.sessionStorage.retrieve('loggeduser');
  }

  getCurrentLoggedVimUser(vimId?: string): any {
    let logged = null;
    if (this.localStorage.retrieve('loggedVimUser') !== null) {
      if (vimId) {
        this.localStorage.retrieve('loggedVimUser').forEach(vim => {
          if (vim.vimId === vimId) {
            logged = vim;
          }
        });
      } else {
        logged = this.localStorage.retrieve('loggedVimUser');
      }
    }
    return logged;
  }

  fetch(): Observable<HttpResponse<Account>> {
    return this.http.get<Account>(SERVER_API_URL + 'api/account', { observe: 'response' });
  }

  save(account: any): Observable<HttpResponse<any>> {
    return this.http.post(SERVER_API_URL + 'api/account', account, { observe: 'response' });
  }

  authenticate(identity) {
    this.userIdentity = identity;
    this.authenticated = identity !== null;
    this.authenticationState.next(this.userIdentity);
  }

  hasAnyAuthority(authorities: string[]): boolean {
    if (!this.authenticated || !this.userIdentity || !this.userIdentity.authorities) {
      return true;
    }

    for (let i = 0; i < authorities.length; i++) {
      if (this.userIdentity.authorities.includes(authorities[i])) {
        return true;
      }
    }

    return false;
  }

  // hasAuthority(authority: string): Promise<boolean> {
  //   if (!this.authenticated) {
  //     return Promise.resolve(false);
  //   }

  //   return this.identity().then(
  //     id => {
  //       return Promise.resolve(id.role && id.role.includes(authority));
  //     },
  //     () => {
  //       return Promise.resolve(false);
  //     }
  //   );
  // }

  hasAuthority(authority: string): boolean {
    const acc: MyAccount = this.localStorage.retrieve('loggedUser') || this.sessionStorage.retrieve('loggedUser');
    let result = false;
    acc.role.forEach(item => {
      if (item['authority'] === authority) {
        result = true;
      }
    });
    return result;
  }

  identity(force?: boolean): Promise<MyAccount> {
    force = true;
    if (force) {
      this.userIdentity = undefined;
    }

    // check and see if we have retrieved the userIdentity data from the server.
    // if we have, reuse it by immediately resolving
    if (this.userIdentity) {
      return Promise.resolve(this.userIdentity);
    }

    // retrieve the userIdentity data from the server, update the identity object, and then resolve.
    let acc: MyAccount = this.localStorage.retrieve('loggedUser') || this.sessionStorage.retrieve('loggedUser');

    const now = new Date().getTime();
    if (acc && now - acc.loggedTime > acc.expires_in * 1000) {
      this.localStorage.clear('authenticationtoken');
      this.sessionStorage.clear('authenticationtoken');
      this.localStorage.clear('loggeduser');
      this.sessionStorage.clear('loggeduser');
      this.localStorage.clear('loggedVimUser');
      this.localStorage.clear('vimprojects');
      sessionStorage.removeItem(VIM_CONNECTION_INFO_KEY);
    }
    acc = this.localStorage.retrieve('loggedUser') || this.sessionStorage.retrieve('loggedUser');

    return of(acc)
      .toPromise()
      .then(account => {
        if (account) {
          this.userIdentity = account;
          this.authenticated = true;
        } else {
          this.userIdentity = null;
          this.authenticated = false;
        }
        this.authenticationState.next(this.userIdentity);
        return this.userIdentity;
      })
      .catch(err => {
        this.userIdentity = null;
        this.authenticated = false;
        this.authenticationState.next(this.userIdentity);
        return null;
      });
    /*return this.fetch()
      .toPromise()
      .then(response => {
        const account: Account = response.body;
        if (account) {
          this.userIdentity = account;
          this.authenticated = true;
          this.trackerService.connect();
          // After retrieve the account info, the language will be changed to
          // the user's preferred language configured in the account setting
          if (this.userIdentity.langKey) {
            const langKey = this.sessionStorage.retrieve('locale') || this.userIdentity.langKey;
            this.languageService.changeLanguage(langKey);
          }
        } else {
          this.userIdentity = null;
          this.authenticated = false;
        }
        this.authenticationState.next(this.userIdentity);
        return this.userIdentity;
      })
      .catch(err => {
        if (this.trackerService.stompClient && this.trackerService.stompClient.connected) {
          this.trackerService.disconnect();
        }
        this.userIdentity = null;
        this.authenticated = false;
        this.authenticationState.next(this.userIdentity);
        return null;
      });*/
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  isIdentityResolved(): boolean {
    return this.userIdentity !== undefined;
  }

  getAuthenticationState(): Observable<any> {
    return this.authenticationState.asObservable();
  }

  getImageUrl(): string {
    return this.isIdentityResolved() ? this.userIdentity.imageUrl : null;
  }

  logout() {
    this.localStorage.clear('authenticationtoken');
    this.sessionStorage.clear('authenticationtoken');
    this.localStorage.clear('loggeduser');
    this.sessionStorage.clear('loggeduser');
    this.localStorage.clear('loggedVimUser');
    this.localStorage.clear('vimprojects');
    this.localStorage.clear('hypervisors');
    this.localStorage.clear('notifications');
    localStorage.removeItem(VIM_CONNECTION_INFO_KEY);
    this.sessionStorage.clear();
  }

  logoutVim(vimId: string) {
    this.localStorage.clear('hypervisors');
    const vimStorage = this.localStorage.retrieve('loggedVimUser');
    const currentVim = this.getCurrentLoggedVimUser(vimId);
    vimStorage.forEach(logged => {
      if (logged['vimId'] === vimId && currentVim) {
        const index = vimStorage.indexOf(currentVim);
        vimStorage.splice(index, 1);
        this.localStorage.store('loggedVimUser', vimStorage);
        this.eventManager.broadcast({ name: 'logoutVim', content: vimId });
      }
    });
  }
}
