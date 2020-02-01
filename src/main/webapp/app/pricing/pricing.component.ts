import { Component, OnInit } from '@angular/core';
import { TierType, AccountService, Account } from 'app/core';

@Component({
  selector: 'jhi-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['pricing.component.scss']
})
export class PricingComponent implements OnInit {
  tierType = TierType;
  account: Account;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }
}
