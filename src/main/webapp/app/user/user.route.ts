import { Routes } from '@angular/router';

import { UserComponent } from './user.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { UserStoreAccessService } from 'app/core/auth/user-store-access-service';

export const userRoute: Routes = [
  {
    path: 'user/:username',
    component: UserComponent,
    data: {
      authorities: [],
      pageTitle: 'OSRS Names'
    },
    canActivate: [UserRouteAccessService, UserStoreAccessService]
  }
];
