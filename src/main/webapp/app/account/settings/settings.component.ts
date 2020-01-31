import { Component, OnInit } from '@angular/core';

import { AccountService } from 'app/core';
import { Account } from 'app/core/user/account.model';
import { GRAVATAR_BASE_URL, GRAVATAR_AVATAR_PATH, GRAVATAR_PARAMETERS } from 'app/shared/constants/gravatar.constants';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
  account: Account;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.account = account;
    });
  }

  getGravatarImageURL() {
    return GRAVATAR_BASE_URL + GRAVATAR_AVATAR_PATH + this.account.imageUrl + GRAVATAR_PARAMETERS;
  }

  cancelSubscription() {
    this.accountService.cancelSubscription().subscribe(res => {
      console.log(res);
    });
  }
}
