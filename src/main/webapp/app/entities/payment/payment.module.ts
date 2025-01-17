import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OsrsnamesSharedModule } from 'app/shared';
import {
  PaymentComponent,
  PaymentDetailComponent,
  PaymentUpdateComponent,
  PaymentDeletePopupComponent,
  PaymentDeleteDialogComponent,
  paymentRoute,
  paymentPopupRoute
} from './';

const ENTITY_STATES = [...paymentRoute, ...paymentPopupRoute];

@NgModule({
  imports: [OsrsnamesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PaymentComponent,
    PaymentDetailComponent,
    PaymentUpdateComponent,
    PaymentDeleteDialogComponent,
    PaymentDeletePopupComponent
  ],
  entryComponents: [PaymentComponent, PaymentUpdateComponent, PaymentDeleteDialogComponent, PaymentDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OsrsnamesPaymentModule {}
