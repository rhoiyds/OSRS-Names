import { Routes, ActivatedRouteSnapshot, RouterStateSnapshot, Resolve } from '@angular/router';

import { PricingComponent } from './pricing.component';
import { PayPalPopupComponent } from 'app/shared/components/paypal-dialog/paypal-dialog.component';
import { UserRouteAccessService } from 'app/core';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IPlan, Plan } from 'app/shared/model/plan.model';
import { PricingService } from './pricing.service';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class PricingResolve implements Resolve<IPlan> {
  constructor(private service: PricingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPlan> {
    const name = route.params['tier'] ? route.params['tier'] : null;
    if (name) {
      return this.service.get().pipe(
        filter((response: HttpResponse<Plan[]>) => response.ok),
        map((response: HttpResponse<Plan[]>) => response.body.filter(plan => plan.name.toLowerCase() === name.toLowerCase())[0])
      );
    }
    return of(new Plan());
  }
}

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
    resolve: {
      plan: PricingResolve
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
