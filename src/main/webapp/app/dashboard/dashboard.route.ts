import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { UserDashboardRerouteService } from 'app/core/auth/user-dashboard-reroute-service';

export const dashboardRoute: Routes = [
  {
    path: '',
    component: DashboardComponent,
    data: {
      authorities: [],
      pageTitle: 'OSRS Names'
    },
    canActivate: [UserDashboardRerouteService]
  }
];
