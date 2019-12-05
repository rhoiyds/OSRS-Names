import { Component, Input } from '@angular/core';

import { IUser } from 'app/core/user/user.model';

@Component({
  selector: 'jhi-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent {
  @Input() user: IUser;

  constructor() {}
}
