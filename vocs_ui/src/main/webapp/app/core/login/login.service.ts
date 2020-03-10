import { Injectable } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';

@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {}

  login(credentials, callback?) {
    const cb = callback || function() {};

    return new Promise((resolve, reject) => {
      this.authServerProvider.login(credentials).subscribe(
        data => {
          resolve(data);
          /*this.accountService.identity(true).then(account => {
            // this.trackerService.sendActivity();
          });*/
          return cb();
        },
        err => {
          this.logout();
          reject(err);
          return cb(err);
        }
      );
    });
  }

  loginVim(credentials, vimId: string, vimName: string, isStorableVimUser: boolean, callback?) {
    const cb = callback || function() {};

    return new Promise((resolve, reject) => {
      this.authServerProvider.loginVim(credentials, vimId, vimName, isStorableVimUser).subscribe(
        data => {
          resolve(data);
          return cb();
        },
        err => {
          this.logout();
          reject(err);
          return cb(err);
        }
      );
    });
  }

  loginWithToken(username, loggedUser, jwt, rememberMe) {
    return this.authServerProvider.loginWithToken(username, loggedUser, jwt, rememberMe);
  }

  logout() {
    this.authServerProvider.logout().subscribe(null, null, () => this.accountService.authenticate(null));
  }
}
