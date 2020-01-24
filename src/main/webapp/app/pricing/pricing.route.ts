import { Routes } from '@angular/router';

import { PricingComponent } from './pricing.component';
import { PayPalPopupComponent } from 'app/shared/components/paypal-dialog/paypal-dialog.component';
import { UserRouteAccessService } from 'app/core';

export const pricingRoute: Routes = [
  {
    path: '',
    component: PricingComponent,
    data: {
      authorities: [],
      pageTitle: 'OSRS Names'
    }
  },
  {
    path: ':tier',
    component: PayPalPopupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Upgrade'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
