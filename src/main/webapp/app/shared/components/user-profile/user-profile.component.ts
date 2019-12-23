import { Component, Input, OnInit } from '@angular/core';
import { Md5 } from 'ts-md5/dist/md5';
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
    const hash = Md5.hashStr(this.user.email.trim().toLowerCase());
    return GRAVATAR_BASE_URL + GRAVATAR_AVATAR_PATH + hash + GRAVATAR_PARAMETERS;
  }
}
