import { Injectable, isDevMode } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { AccountService } from 'app/core/';

@Injectable({ providedIn: 'root' })
export class UserDashboardRerouteService implements CanActivate {
  constructor(private router: Router, private accountService: AccountService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    return this.checkLogin();
  }

  checkLogin(): Promise<boolean> {
    return this.accountService.identity().then(account => {
      if (account) {
        return true;
      }
      this.router.navigate(['/home']);
      return false;
    });
  }
}
