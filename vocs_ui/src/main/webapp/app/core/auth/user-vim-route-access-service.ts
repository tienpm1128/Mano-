import { Injectable } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AccountService, StateStorageService } from 'app/core';

@Injectable({ providedIn: 'root' })
export class UserVimRouteAccessService implements CanActivate {
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private stateStorageService: StateStorageService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    const vimUser = this.accountService.getCurrentLoggedVimUser(route.params['vimInstanceId']);
    if (vimUser) {
      return true;
    } else {
      if (!/\/vims\/\d+\/login/g.test(state.url)) {
        this.stateStorageService.storeUrl(state.url);
        this.router.navigate(['/vims', route.params['vimInstanceId'], 'login']);
        return false;
      }
      return true;
    }
  }
}
