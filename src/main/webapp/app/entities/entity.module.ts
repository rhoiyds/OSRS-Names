import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        loadChildren: './listing/listing.module#RsnsalesListingModule'
      },
      {
        path: 'listing',
        loadChildren: './listing/listing.module#RsnsalesListingModule'
      },
      {
        path: 'offer',
        loadChildren: './offer/offer.module#RsnsalesOfferModule'
      },
      {
        path: 'tag',
        loadChildren: './tag/tag.module#RsnsalesTagModule'
      },
      {
        path: 'comment',
        loadChildren: './comment/comment.module#RsnsalesCommentModule'
      },
      {
        path: 'trade',
        loadChildren: './trade/trade.module#RsnsalesTradeModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RsnsalesEntityModule {}
