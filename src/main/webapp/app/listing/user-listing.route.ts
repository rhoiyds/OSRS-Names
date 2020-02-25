import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Listing } from 'app/shared/model/listing.model';
import { UserListingService } from './user-listing.service';
import { UserListingComponent } from './user-listing.component';
import { UserListingDetailComponent } from './user-listing-detail.component';
import { UserListingUpdateComponent } from './user-listing-update.component';
import { UserListingDeletePopupComponent } from './user-listing-delete-dialog.component';
import { IListing } from 'app/shared/model/listing.model';
import { UserListingOfferPopupComponent } from './user-listing-offer-dialog.component';
import { TotalListingsAccessService } from 'app/core/auth/total-listings-access-service';

@Injectable({ providedIn: 'root' })
export class ListingResolve implements Resolve<IListing> {
  constructor(private service: UserListingService) {}

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
    component: UserListingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Listings'
    },
    canActivate: []
  },
  {
    path: ':id/view',
    component: UserListingDetailComponent,
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
    component: UserListingUpdateComponent,
    resolve: {
      listing: ListingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Listings'
    },
    canActivate: [UserRouteAccessService, TotalListingsAccessService]
  },
  {
    path: ':id/edit',
    component: UserListingUpdateComponent,
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
    component: UserListingDeletePopupComponent,
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
    component: UserListingOfferPopupComponent,
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
