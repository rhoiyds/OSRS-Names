import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService, IUser, TierType, AccountService } from 'app/core';
import { RatingService } from 'app/entities/rating';
import { UserListingService } from 'app/listing';
import { IListing, ListingType } from 'app/shared/model/listing.model';
import { ListingService } from 'app/entities/listing';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  showTab = 'buying';

  user: IUser;
  buyingListings: IListing[];
  sellingListings: IListing[];
  averageRating: number;

  listingType = ListingType;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService,
    protected ratingService: RatingService,
    protected listingService: UserListingService,
    protected listingEntityService: ListingService,
    protected router: Router,
    protected accountService: AccountService
  ) {}

  ngOnInit() {
    this.accountService.identity().then(account => {
      if (account) {
        this.loadAll(account.id);
      }
    });
  }

  loadAll(id) {
    this.listingEntityService
      .query({
        'ownerId.equals': id,
        'active.equals': true
      })
      .subscribe(listingRes => {
        this.sellingListings = listingRes.body.filter(listing => listing.type === ListingType.HAVE);
        this.buyingListings = listingRes.body.filter(listing => listing.type === ListingType.WANT);
      });
  }
}
