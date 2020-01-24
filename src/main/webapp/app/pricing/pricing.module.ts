import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OsrsnamesSharedModule } from 'app/shared';
import { PricingComponent } from './pricing.component';
import { pricingRoute } from './pricing.route';

@NgModule({
  imports: [OsrsnamesSharedModule, RouterModule.forChild(pricingRoute)],
  declarations: [PricingComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OsrsnamesPricingModule {}
