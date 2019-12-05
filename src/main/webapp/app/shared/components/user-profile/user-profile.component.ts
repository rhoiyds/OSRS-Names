import { Component, Input } from '@angular/core';
import { Md5 } from 'ts-md5/dist/md5';
import { GRAVATAR_AVATAR_PATH, GRAVATAR_BASE_URL, GRAVATAR_PARAMETERS } from 'app/shared/constants/gravatar.constants';

import { IUser } from 'app/core/user/user.model';

@Component({
  selector: 'jhi-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent {
  @Input() user: IUser;

  constructor() {}

  getGravatarImageURL() {
    const hash = Md5.hashStr(this.user.email.trim().toLowerCase());
    return GRAVATAR_BASE_URL + GRAVATAR_AVATAR_PATH + hash + GRAVATAR_PARAMETERS;
  }
}
