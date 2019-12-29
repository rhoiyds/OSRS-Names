import { Component, Input, OnInit } from '@angular/core';
import { GRAVATAR_AVATAR_PATH, GRAVATAR_BASE_URL, GRAVATAR_PARAMETERS } from 'app/shared/constants/gravatar.constants';

import { IUser } from 'app/core/user/user.model';
import { RatingService } from 'app/entities/rating';

@Component({
  selector: 'jhi-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  @Input() user: IUser;
  @Input() timestamp;
  rating: number;

  constructor(protected ratingsService: RatingService) {}

  ngOnInit() {
    this.ratingsService.getAverageRatingForUser(this.user.id).subscribe(response => {
      this.rating = response.body;
    });
  }

  getGravatarImageURL() {
    return GRAVATAR_BASE_URL + GRAVATAR_AVATAR_PATH + this.user.imageUrl + GRAVATAR_PARAMETERS;
  }
}
