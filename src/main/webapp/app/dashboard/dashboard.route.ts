import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';

export const dashboardRoute: Routes = [
  {
    path: '',
    component: DashboardComponent,
    data: {
      authorities: [],
      pageTitle: 'OSRS Names'
    }
  }
];
