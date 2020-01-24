import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { userRoute } from './user/user.route';

const LAYOUT_ROUTES = [navbarRoute, ...userRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          loadChildren: './admin/admin.module#OsrsnamesAdminModule'
        },
        {
          path: 'pricing',
          loadChildren: './pricing/pricing.module#OsrsnamesPricingModule'
        },
        ...LAYOUT_ROUTES
      ],
      { enableTracing: DEBUG_INFO_ENABLED, anchorScrolling: 'enabled', onSameUrlNavigation: 'reload', scrollPositionRestoration: 'enabled' }
    )
  ],
  exports: [RouterModule]
})
export class OsrsnamesAppRoutingModule {}
