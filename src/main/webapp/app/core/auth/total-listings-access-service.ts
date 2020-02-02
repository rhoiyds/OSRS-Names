import { Injectable, isDevMode } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { AccountService, TierType } from 'app/core/';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { ListingService } from 'app/entities/listing';
import { JhiAlertService } from 'ng-jhipster';

@Injectable({ providedIn: 'root' })
export class TotalListingsAccessService implements CanActivate {
  constructor(
    private loginModalService: LoginModalService,
    private accountService: AccountService,
    private listingService: ListingService,
    private router: Router,
    private alertService: JhiAlertService
  ) {}

  private ERROR_MESSAGE =
    'Maximum listings for your current account subscription tier has been reached. You will have to upgrade your plan to continue.';

  canActivate(): boolean | Promise<boolean> {
    return this.checkTotalListings();
  }

  checkTotalListings(): Promise<boolean> {
    return this.listingService
      .getTotalListingsCount()
      .toPromise()
      .then(response => {
        console.log('User has', response.body, 'listings and account is', this.accountService.getTier());
        const totalListingsCount = response.body;
        const tier = this.accountService.getTier();
        if (tier === TierType.FREE) {
          if (totalListingsCount >= 3) {
            this.alertService.error(this.ERROR_MESSAGE);
            return false;
          }
        }
        if (tier === TierType.STARTER) {
          if (totalListingsCount >= 20) {
            this.alertService.error(this.ERROR_MESSAGE);
            return false;
          }
        }
        return true;
      });
  }
}
