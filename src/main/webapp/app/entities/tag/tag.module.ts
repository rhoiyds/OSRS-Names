import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OsrsnamesSharedModule } from 'app/shared';
import {
  TagComponent,
  TagDetailComponent,
  TagUpdateComponent,
  TagDeletePopupComponent,
  TagDeleteDialogComponent,
  tagRoute,
  tagPopupRoute
} from './';

const ENTITY_STATES = [...tagRoute, ...tagPopupRoute];

@NgModule({
  imports: [OsrsnamesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TagComponent, TagDetailComponent, TagUpdateComponent, TagDeleteDialogComponent, TagDeletePopupComponent],
  entryComponents: [TagComponent, TagUpdateComponent, TagDeleteDialogComponent, TagDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OsrsnamesTagModule {}
