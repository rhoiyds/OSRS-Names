import { NgModule } from '@angular/core';

import { OsrsnamesSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [OsrsnamesSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [OsrsnamesSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class OsrsnamesSharedCommonModule {}
