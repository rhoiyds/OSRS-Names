import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RsnsalesSharedModule } from 'app/shared';
import {
  MiddlemanRequestComponent,
  MiddlemanRequestDetailComponent,
  MiddlemanRequestUpdateComponent,
  MiddlemanRequestDeletePopupComponent,
  MiddlemanRequestDeleteDialogComponent,
  middlemanRequestRoute,
  middlemanRequestPopupRoute
} from './';

const ENTITY_STATES = [...middlemanRequestRoute, ...middlemanRequestPopupRoute];

@NgModule({
  imports: [RsnsalesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MiddlemanRequestComponent,
    MiddlemanRequestDetailComponent,
    MiddlemanRequestUpdateComponent,
    MiddlemanRequestDeleteDialogComponent,
    MiddlemanRequestDeletePopupComponent
  ],
  entryComponents: [
    MiddlemanRequestComponent,
    MiddlemanRequestUpdateComponent,
    MiddlemanRequestDeleteDialogComponent,
    MiddlemanRequestDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RsnsalesMiddlemanRequestModule {}
