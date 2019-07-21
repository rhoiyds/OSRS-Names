import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMiddlemanRequest } from 'app/shared/model/middleman-request.model';

type EntityResponseType = HttpResponse<IMiddlemanRequest>;
type EntityArrayResponseType = HttpResponse<IMiddlemanRequest[]>;

@Injectable({ providedIn: 'root' })
export class MiddlemanRequestService {
  public resourceUrl = SERVER_API_URL + 'api/middleman-requests';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/middleman-requests';

  constructor(protected http: HttpClient) {}

  create(middlemanRequest: IMiddlemanRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(middlemanRequest);
    return this.http
      .post<IMiddlemanRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(middlemanRequest: IMiddlemanRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(middlemanRequest);
    return this.http
      .put<IMiddlemanRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMiddlemanRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMiddlemanRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMiddlemanRequest[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(middlemanRequest: IMiddlemanRequest): IMiddlemanRequest {
    const copy: IMiddlemanRequest = Object.assign({}, middlemanRequest, {
      timestamp: middlemanRequest.timestamp != null && middlemanRequest.timestamp.isValid() ? middlemanRequest.timestamp.toJSON() : null
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
      res.body.forEach((middlemanRequest: IMiddlemanRequest) => {
        middlemanRequest.timestamp = middlemanRequest.timestamp != null ? moment(middlemanRequest.timestamp) : null;
      });
    }
    return res;
  }
}
