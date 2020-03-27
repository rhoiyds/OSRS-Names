import { Route } from '@angular/router';

import { HomeComponent } from './';

export const HOME_ROUTE: Route = {
  path: 'home',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'OSRS Names - Buy and sell Old School Runescape usernames!'
  }
};
