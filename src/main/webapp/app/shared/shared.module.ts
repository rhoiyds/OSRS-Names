import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { OsrsnamesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { ListingOfferComponent } from './components/listing-offer/listing-offer.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { OsrsCurrencyPipe } from './osrs-currency.pipe';
import { RatingSelectionDialogComponent } from 'app/shared/components/rating-selection/rating-selection-dialog.component';
import { TimeAgoPipe } from 'time-ago-pipe';

@NgModule({
  imports: [OsrsnamesSharedCommonModule],
  declarations: [
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    ListingOfferComponent,
    UserProfileComponent,
    OsrsCurrencyPipe,
    RatingSelectionDialogComponent,
    TimeAgoPipe
  ],
  entryComponents: [JhiLoginModalComponent, RatingSelectionDialogComponent],
  exports: [
    OsrsnamesSharedCommonModule,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    ListingOfferComponent,
    UserProfileComponent,
    OsrsCurrencyPipe,
    RatingSelectionDialogComponent,
    TimeAgoPipe
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
