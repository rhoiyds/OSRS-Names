import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RsnsalesSharedModule } from 'app/shared';
import {
  ListingComponent,
  ListingDetailComponent,
  ListingUpdateComponent,
  ListingDeletePopupComponent,
  ListingDeleteDialogComponent,
  ListingOfferDialogComponent,
  ListingOfferPopupComponent,
  listingRoute,
  listingPopupRoute
} from './';

const ENTITY_STATES = [...listingRoute, ...listingPopupRoute];

@NgModule({
  imports: [RsnsalesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ListingComponent,
    ListingDetailComponent,
    ListingUpdateComponent,
    ListingDeleteDialogComponent,
    ListingDeletePopupComponent,
    ListingOfferDialogComponent,
    ListingOfferPopupComponent
  ],
  entryComponents: [
    ListingComponent,
    ListingUpdateComponent,
    ListingDeleteDialogComponent,
    ListingDeletePopupComponent,
    ListingOfferDialogComponent,
    ListingOfferPopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RsnsalesListingModule {}
