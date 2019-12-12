import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Listing } from 'app/shared/model/listing.model';
import { ListingService } from './listing.service';
import { ListingComponent } from './listing.component';
import { ListingDetailComponent } from './listing-detail.component';
import { ListingUpdateComponent } from './listing-update.component';
import { ListingDeletePopupComponent } from './listing-delete-dialog.component';
import { IListing } from 'app/shared/model/listing.model';
import { ListingOfferPopupComponent } from './listing-offer-dialog.component';

@Injectable({ providedIn: 'root' })
export class ListingResolve implements Resolve<IListing> {
  constructor(private service: ListingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IListing> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Listing>) => response.ok),
        map((listing: HttpResponse<Listing>) => listing.body)
      );
    }
    return of(new Listing());
  }
}

export const listingRoute: Routes = [
  {
    path: '',
    component: ListingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Listings'
    },
    canActivate: []
  },
  {
    path: ':id/view',
    component: ListingDetailComponent,
    resolve: {
      listing: ListingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Listings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ListingUpdateComponent,
    resolve: {
      listing: ListingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Listings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ListingUpdateComponent,
    resolve: {
      listing: ListingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Listings'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const listingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ListingDeletePopupComponent,
    resolve: {
      listing: ListingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Listings'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: ':id/offer',
    component: ListingOfferPopupComponent,
    resolve: {
      listing: ListingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Listings'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
