import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITrade } from 'app/shared/model/trade.model';
import { IRating } from 'app/shared/model/rating.model';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<ITrade>;
type RatingResponseType = HttpResponse<IRating>;
type EntityArrayResponseType = HttpResponse<ITrade[]>;

@Injectable({ providedIn: 'root' })
export class TradeService {
  public resourceUrl = SERVER_API_URL + 'api/trades';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/trades';

  constructor(protected http: HttpClient) {}

  create(trade: ITrade): Observable<EntityResponseType> {
    return this.http.post<ITrade>(this.resourceUrl, trade, { observe: 'response' });
  }

  update(trade: ITrade): Observable<EntityResponseType> {
    return this.http.put<ITrade>(this.resourceUrl, trade, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrade[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  rate(id, rating): Observable<EntityResponseType> {
    return this.http
      .put<IRating>(`${this.resourceUrl}/${id}/rate`, rating, { observe: 'response' })
      .pipe(map((res: RatingResponseType) => this.convertDateFromServer(res)));
  }

  protected convertDateFromServer(res: RatingResponseType): RatingResponseType {
    if (res.body) {
      res.body.timestamp = res.body.timestamp != null ? moment(res.body.timestamp) : null;
    }
    return res;
  }
}
