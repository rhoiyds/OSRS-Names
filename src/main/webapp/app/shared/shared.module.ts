import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { OsrsnamesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { ListingOfferComponent } from './components/listing-offer/listing-offer.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { OsrsCurrencyPipe } from './osrs-currency.pipe';
import { RatingSelectionDialogComponent } from 'app/shared/components/rating-selection/rating-selection-dialog.component';
import { TimeAgoPipe } from 'time-ago-pipe';
import { PayPalModalComponent } from './components/paypal-modal/paypal-modal.component';

@NgModule({
  imports: [OsrsnamesSharedCommonModule],
  declarations: [
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    ListingOfferComponent,
    UserProfileComponent,
    OsrsCurrencyPipe,
    RatingSelectionDialogComponent,
    TimeAgoPipe,
    PayPalModalComponent
  ],
  entryComponents: [JhiLoginModalComponent, RatingSelectionDialogComponent, PayPalModalComponent],
  exports: [
    OsrsnamesSharedCommonModule,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    ListingOfferComponent,
    UserProfileComponent,
    OsrsCurrencyPipe,
    RatingSelectionDialogComponent,
    TimeAgoPipe,
    PayPalModalComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OsrsnamesSharedModule {
  static forRoot() {
    return {
      ngModule: OsrsnamesSharedModule
    };
  }
}
