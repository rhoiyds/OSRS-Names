import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RsnsalesSharedModule } from 'app/shared';
import {
  ListingComponent,
  ListingDetailComponent,
  ListingUpdateComponent,
  ListingDeletePopupComponent,
  ListingDeleteDialogComponent,
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
    ListingDeletePopupComponent
  ],
  entryComponents: [ListingComponent, ListingUpdateComponent, ListingDeleteDialogComponent, ListingDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RsnsalesListingModule {}
