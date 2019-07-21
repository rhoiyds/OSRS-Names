import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RsnsalesSharedModule } from 'app/shared';
import {
  OfferComponent,
  OfferDetailComponent,
  OfferUpdateComponent,
  OfferDeletePopupComponent,
  OfferDeleteDialogComponent,
  offerRoute,
  offerPopupRoute
} from './';

const ENTITY_STATES = [...offerRoute, ...offerPopupRoute];

@NgModule({
  imports: [RsnsalesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [OfferComponent, OfferDetailComponent, OfferUpdateComponent, OfferDeleteDialogComponent, OfferDeletePopupComponent],
  entryComponents: [OfferComponent, OfferUpdateComponent, OfferDeleteDialogComponent, OfferDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RsnsalesOfferModule {}
