import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Trade } from 'app/shared/model/trade.model';
import { TradeService } from './trade.service';
import { TradeComponent } from './trade.component';
import { TradeDetailComponent } from './trade-detail.component';
import { TradeUpdateComponent } from './trade-update.component';
import { TradeDeletePopupComponent } from './trade-delete-dialog.component';
import { ITrade } from 'app/shared/model/trade.model';

@Injectable({ providedIn: 'root' })
export class TradeResolve implements Resolve<ITrade> {
  constructor(private service: TradeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITrade> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Trade>) => response.ok),
        map((trade: HttpResponse<Trade>) => trade.body)
      );
    }
    return of(new Trade());
  }
}

export const tradeRoute: Routes = [
  {
    path: '',
    component: TradeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trades'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TradeDetailComponent,
    resolve: {
      trade: TradeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trades'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TradeUpdateComponent,
    resolve: {
      trade: TradeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trades'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TradeUpdateComponent,
    resolve: {
      trade: TradeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trades'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tradePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TradeDeletePopupComponent,
    resolve: {
      trade: TradeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trades'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
