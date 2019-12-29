import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OsrsnamesSharedModule } from 'app/shared';
import {
  TradeComponent,
  TradeDetailComponent,
  TradeUpdateComponent,
  TradeDeletePopupComponent,
  TradeDeleteDialogComponent,
  tradeRoute,
  tradePopupRoute
} from './';

const ENTITY_STATES = [...tradeRoute, ...tradePopupRoute];

@NgModule({
  imports: [OsrsnamesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TradeComponent, TradeDetailComponent, TradeUpdateComponent, TradeDeleteDialogComponent, TradeDeletePopupComponent],
  entryComponents: [TradeComponent, TradeUpdateComponent, TradeDeleteDialogComponent, TradeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OsrsnamesTradeModule {}
