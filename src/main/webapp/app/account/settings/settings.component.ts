import { Component, OnInit } from '@angular/core';

import { AccountService, TierType } from 'app/core';
import { Account } from 'app/core/user/account.model';
import { GRAVATAR_BASE_URL, GRAVATAR_AVATAR_PATH, GRAVATAR_PARAMETERS } from 'app/shared/constants/gravatar.constants';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
  account: Account;
  tierType = TierType;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.account = account;
    });
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  getGravatarImageURL() {
    return GRAVATAR_BASE_URL + GRAVATAR_AVATAR_PATH + (this.account ? this.account.imageUrl : '') + GRAVATAR_PARAMETERS;
  }

  cancelSubscription() {
    this.accountService.cancelSubscription().subscribe(res => {
      this.accountService.identity(true);
    });
  }

  reviseSubscription(tier: TierType) {
    this.accountService.reviseSubscription(tier).subscribe(res => {
      this.accountService.identity(true);
    });
  }
}
