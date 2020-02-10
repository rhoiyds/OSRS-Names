import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService, IUser, TierType } from 'app/core';
import { RatingService } from 'app/entities/rating';
import { ListingService } from 'app/entities/listing';
import { IRating } from 'app/shared/model/rating.model';
import { IListing, ListingType } from 'app/shared/model/listing.model';
import { GRAVATAR_BASE_URL, GRAVATAR_AVATAR_PATH, GRAVATAR_PARAMETERS } from 'app/shared/constants/gravatar.constants';

@Component({
  selector: 'jhi-user',
  templateUrl: './user.component.html',
  styleUrls: ['user.component.scss']
})
export class UserComponent implements OnInit {
  showTab = 'ratings';

  user: IUser;
  ratings: IRating[];
  listings: IListing[];
  averageRating: number;

  listingType = ListingType;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService,
    protected ratingService: RatingService,
    protected listingService: ListingService,
    protected router: Router
  ) {}

  ngOnInit() {
    this.activatedRoute.params.subscribe(routeParams => {
      this.loadAll(routeParams.username);
    });
  }

  onProfileClick(listing) {
    if (listing.owner.tier === TierType.PRO) {
      this.router.navigate(['/user', listing.owner.username]);
      return;
    }
    this.router.navigate(['/listing', listing.id, 'view']);
  }

  loadAll(username) {
    this.userService.find(username).subscribe(res => {
      this.user = res.body;
      this.ratingService
        .query({
          'recipientId.equals': this.user.id
        })
        .subscribe(ratingRes => {
          this.ratings = ratingRes.body;
        });
      this.ratingService.getAverageRatingForUser(this.user.id).subscribe(averageRatingRespone => {
        this.averageRating = averageRatingRespone.body;
      });
      this.listingService
        .query({
          'ownerId.equals': this.user.id
        })
        .subscribe(listingRes => {
          this.listings = listingRes.body;
        });
    });
  }

  getGravatarImageURL() {
    return GRAVATAR_BASE_URL + GRAVATAR_AVATAR_PATH + this.user.imageUrl + GRAVATAR_PARAMETERS;
  }
}
