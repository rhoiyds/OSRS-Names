import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

import { IListing, ListingType } from 'app/shared/model/listing.model';
import { TierType } from 'app/core';

@Component({
  selector: 'jhi-listing-list',
  templateUrl: './listing-list.component.html'
})
export class ListingListComponent implements OnInit {
  @Input() listings: IListing[];
  listingType = ListingType;
  tierType = TierType;

  constructor(protected router: Router) {
    this.listings = [];
  }

  ngOnInit() {}

  loadAll() {}

  onProfileClick(listing) {
    if (listing.owner.tier === TierType.PRO) {
      this.router.navigate(['/user', listing.owner.username]);
      return;
    }
    this.router.navigate(['/listing', listing.id, 'view']);
  }
}
