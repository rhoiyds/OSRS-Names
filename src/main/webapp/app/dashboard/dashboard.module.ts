import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OsrsnamesSharedModule } from 'app/shared';
import { DashboardComponent } from './dashboard.component';
import { dashboardRoute } from './dashboard.route';

@NgModule({
  imports: [OsrsnamesSharedModule, RouterModule.forChild(dashboardRoute)],
  declarations: [DashboardComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OsrsnamesDashboardModule {}
