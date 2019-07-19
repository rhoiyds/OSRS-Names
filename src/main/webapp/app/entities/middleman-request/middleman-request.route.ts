import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MiddlemanRequest } from 'app/shared/model/middleman-request.model';
import { MiddlemanRequestService } from './middleman-request.service';
import { MiddlemanRequestComponent } from './middleman-request.component';
import { MiddlemanRequestDetailComponent } from './middleman-request-detail.component';
import { MiddlemanRequestUpdateComponent } from './middleman-request-update.component';
import { MiddlemanRequestDeletePopupComponent } from './middleman-request-delete-dialog.component';
import { IMiddlemanRequest } from 'app/shared/model/middleman-request.model';

@Injectable({ providedIn: 'root' })
export class MiddlemanRequestResolve implements Resolve<IMiddlemanRequest> {
  constructor(private service: MiddlemanRequestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMiddlemanRequest> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MiddlemanRequest>) => response.ok),
        map((middlemanRequest: HttpResponse<MiddlemanRequest>) => middlemanRequest.body)
      );
    }
    return of(new MiddlemanRequest());
  }
}

export const middlemanRequestRoute: Routes = [
  {
    path: '',
    component: MiddlemanRequestComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MiddlemanRequests'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MiddlemanRequestDetailComponent,
    resolve: {
      middlemanRequest: MiddlemanRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MiddlemanRequests'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MiddlemanRequestUpdateComponent,
    resolve: {
      middlemanRequest: MiddlemanRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MiddlemanRequests'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MiddlemanRequestUpdateComponent,
    resolve: {
      middlemanRequest: MiddlemanRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MiddlemanRequests'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const middlemanRequestPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MiddlemanRequestDeletePopupComponent,
    resolve: {
      middlemanRequest: MiddlemanRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'MiddlemanRequests'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
