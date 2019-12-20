import { Routes } from '@angular/router';

import { PricingComponent } from './pricing.component';

export const pricingRoute: Routes = [
  {
    path: 'pricing',
    component: PricingComponent,
    data: {
      authorities: [],
      pageTitle: 'OSRS Names'
    }
  }
];
