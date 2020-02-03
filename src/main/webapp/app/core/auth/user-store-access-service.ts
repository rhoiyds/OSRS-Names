import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { AccountService, TierType, UserService } from 'app/core/';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { JhiAlertService } from 'ng-jhipster';

@Injectable({ providedIn: 'root' })
export class UserStoreAccessService implements CanActivate {
  private ERROR_MESSAGE = ' does not have a Store.';

  constructor(
    private loginModalService: LoginModalService,
    private accountService: AccountService,
    private userService: UserService,
    private router: Router,
    private alertService: JhiAlertService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {
    return this.checkIfUserIsProTier(route.params.username);
  }

  checkIfUserIsProTier(username: string): Promise<boolean> {
    return this.userService
      .find(username)
      .toPromise()
      .then(response => {
        if (response.body.tier !== TierType.PRO) {
          this.alertService.error(username + this.ERROR_MESSAGE);
          this.router.navigate(['/']);
          return false;
        }
        return true;
      });
  }
}
