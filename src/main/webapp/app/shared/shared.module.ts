import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RsnsalesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { ListingOfferComponent } from './components/listing-offer/listing-offer.component';

@NgModule({
  imports: [RsnsalesSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, ListingOfferComponent],
  entryComponents: [JhiLoginModalComponent],
  exports: [RsnsalesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, ListingOfferComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RsnsalesSharedModule {
  static forRoot() {
    return {
      ngModule: RsnsalesSharedModule
    };
  }
}
