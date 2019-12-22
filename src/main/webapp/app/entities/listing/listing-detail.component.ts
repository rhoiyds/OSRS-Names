import { Component, OnInit, IterableDiffers } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IListing, ListingType } from 'app/shared/model/listing.model';
import { IOffer, OfferStatus } from 'app/shared/model/offer.model';
import { OfferService } from 'app/entities/offer';
import { AccountService, Account } from 'app/core';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-listing-detail',
  templateUrl: './listing-detail.component.html'
})
export class ListingDetailComponent implements OnInit {
  listing: IListing;
  offers: IOffer[];
  currentAccount: Account;
  listingType = ListingType;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private router: Router,
    private offerService: OfferService,
    private accountService: AccountService,
    private eventManager: JhiEventManager
  ) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.currentAccount = account;
      this.activatedRoute.data.subscribe(({ listing }) => {
        this.listing = listing;
        this.loadAll();
      });
    });
    this.registerChangesInListing();
  }

  loadAll() {
    this.getOffersForListing();
  }

  previousState() {
    window.history.back();
  }

  registerChangesInListing() {
    this.eventManager.subscribe('listingModification', response => this.loadAll());
  }

  canAccept() {
    return this.offers.filter(offer => offer.status === OfferStatus.ACCEPTED).length === 0;
  }

  private getOffersForListing() {
    this.offerService.getOffersForListing(this.listing.id).subscribe(offers => {
      this.offers = this.sortedOffers(offers.body);
    });
  }

  private sortedOffers(offers: IOffer[]) {
    const offerOrder = Object.entries(OfferStatus).map(([key, value]) => value);
    return offers.sort((a, b) => offerOrder.indexOf(a) - offerOrder.indexOf(b));
  }
}
