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
export class ListingService {
  public resourceUrl = SERVER_API_URL + 'api/listings';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/listings';

  constructor(protected http: HttpClient) {}

  create(listing: IListing): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(listing);
    return this.http
      .post<IListing>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(listing: IListing): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(listing);
    return this.http
      .put<IListing>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IListing>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IListing[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IListing[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
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
