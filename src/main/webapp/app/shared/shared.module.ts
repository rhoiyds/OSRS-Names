import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { OsrsnamesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { ListingOfferComponent } from './components/listing-offer/listing-offer.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { OsrsCurrencyPipe } from './osrs-currency.pipe';
import { RatingSelectionDialogComponent } from 'app/shared/components/rating-selection/rating-selection-dialog.component';
import { TimeAgoPipe } from 'time-ago-pipe';
import { PayPalDialogComponent, PayPalPopupComponent } from './components/paypal-dialog/paypal-dialog.component';
import { SafePipe } from './safe.pipe';
import { ListingListComponent } from './components/listing-list/listing-list.component';

@NgModule({
  imports: [OsrsnamesSharedCommonModule],
  declarations: [
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    ListingOfferComponent,
    UserProfileComponent,
    OsrsCurrencyPipe,
    SafePipe,
    RatingSelectionDialogComponent,
    TimeAgoPipe,
    PayPalDialogComponent,
    PayPalPopupComponent,
    ListingListComponent
  ],
  entryComponents: [JhiLoginModalComponent, RatingSelectionDialogComponent, PayPalPopupComponent, PayPalDialogComponent],
  exports: [
    OsrsnamesSharedCommonModule,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    ListingOfferComponent,
    UserProfileComponent,
    OsrsCurrencyPipe,
    SafePipe,
    RatingSelectionDialogComponent,
    TimeAgoPipe,
    PayPalDialogComponent,
    PayPalPopupComponent,
    ListingListComponent
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
