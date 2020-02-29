import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';
import { Router } from '@angular/router';
import { UserListingService } from 'app/listing/user-listing.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
  buyingTotal = 0;
  sellingTotal = 0;
  trending = [];

  constructor(
    private accountService: AccountService,
    private eventManager: JhiEventManager,
    private router: Router,
    private listingService: UserListingService,
    private loginModalService: LoginModalService
  ) {}

  ngOnInit() {
    this.registerAuthenticationSuccess();
    this.getStats();
  }

  getStats() {
    this.listingService.getStats().subscribe(res => {
      this.buyingTotal = res.body.buyingTotal;
      this.sellingTotal = res.body.sellingTotal;
      this.trending = res.body.trending;
    });
  }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.router.navigate(['/']);
      });
    });
  }

  onLoginClick() {
    this.loginModalService.open();
  }
}
