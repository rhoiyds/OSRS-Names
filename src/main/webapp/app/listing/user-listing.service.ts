import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IListing } from 'app/shared/model/listing.model';

type EntityResponseType = HttpResponse<IListing>;
type EntityArrayResponseType = HttpResponse<IListing[]>;

@Injectable({ providedIn: 'root' })
export class UserListingService {
  public resourceUrl = SERVER_API_URL + 'api/listings';

  constructor(protected http: HttpClient) {}

  create(listing: IListing): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(listing);
    return this.http
      .post<IListing>(this.resourceUrl + '/add', copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(listing: IListing): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(listing);
    return this.http
      .put<IListing>(this.resourceUrl + '/edit', copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}/deactivate`, { observe: 'response' });
  }

  getTotalListingsCount(): Observable<HttpResponse<number>> {
    return this.http.get<any>(`${this.resourceUrl}/count`, { observe: 'response' });
  }

  getMatchesForListing(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IListing[]>(`${this.resourceUrl}/${id}/matches`, { observe: 'response' });
  }

  getStats() {
    return this.http.get<any>(`${this.resourceUrl}/stats`, { observe: 'response' });
  }

  protected convertDateFromClient(listing: IListing): IListing {
    const copy: IListing = Object.assign({}, listing, {
      timestamp: listing.timestamp != null && listing.timestamp.isValid() ? listing.timestamp.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.timestamp = res.body.timestamp != null ? moment(res.body.timestamp) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((listing: IListing) => {
        listing.timestamp = listing.timestamp != null ? moment(listing.timestamp) : null;
      });
    }
    return res;
  }
}
