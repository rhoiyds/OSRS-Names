import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'listing',
        loadChildren: './listing/listing.module#OsrsnamesListingModule'
      },
      {
        path: 'tag',
        loadChildren: './tag/tag.module#OsrsnamesTagModule'
      },
      {
        path: 'offer',
        loadChildren: './offer/offer.module#OsrsnamesOfferModule'
      },
      {
        path: 'comment',
        loadChildren: './comment/comment.module#OsrsnamesCommentModule'
      },
      {
        path: 'trade',
        loadChildren: './trade/trade.module#OsrsnamesTradeModule'
      },
      {
        path: 'rating',
        loadChildren: './rating/rating.module#OsrsnamesRatingModule'
      },
      {
        path: 'payment',
        loadChildren: './payment/payment.module#OsrsnamesPaymentModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OsrsnamesEntityModule {}
