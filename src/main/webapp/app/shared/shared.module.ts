import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RsnsalesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [RsnsalesSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [RsnsalesSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RsnsalesSharedModule {
  static forRoot() {
    return {
      ngModule: RsnsalesSharedModule
    };
  }
}
