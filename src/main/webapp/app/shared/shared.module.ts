import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { OsrsnamesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [OsrsnamesSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [OsrsnamesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OsrsnamesSharedModule {
  static forRoot() {
    return {
      ngModule: OsrsnamesSharedModule
    };
  }
}
