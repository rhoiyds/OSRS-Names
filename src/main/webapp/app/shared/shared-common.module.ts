import { NgModule } from '@angular/core';

import { RsnsalesSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [RsnsalesSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [RsnsalesSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class RsnsalesSharedCommonModule {}
