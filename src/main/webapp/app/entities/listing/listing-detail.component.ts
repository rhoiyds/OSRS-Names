import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListing } from 'app/shared/model/listing.model';

@Component({
  selector: 'jhi-listing-detail',
  templateUrl: './listing-detail.component.html'
})
export class ListingDetailComponent implements OnInit {
  listing: IListing;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ listing }) => {
      this.listing = listing;
    });
  }

  previousState() {
    window.history.back();
  }
}
