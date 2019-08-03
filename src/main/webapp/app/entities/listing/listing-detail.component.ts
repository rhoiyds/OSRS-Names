import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListing } from 'app/shared/model/listing.model';
import { IOffer } from 'app/shared/model/offer.model';
import { OfferService } from 'app/entities/offer';

@Component({
  selector: 'jhi-listing-detail',
  templateUrl: './listing-detail.component.html'
})
export class ListingDetailComponent implements OnInit {
  listing: IListing;
  offers: IOffer[];

  constructor(protected activatedRoute: ActivatedRoute, private offerService: OfferService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ listing }) => {
      console.log(listing);
      this.listing = listing;
      this.getOffersForListing();
    });
  }

  previousState() {
    window.history.back();
  }

  private getOffersForListing() {
    this.offerService.getOffersForListing(this.listing.id).subscribe(offers => {
      this.offers = offers.body;
    });
  }
}
