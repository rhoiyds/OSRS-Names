import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IPlan } from 'app/shared/model/plan.model';

type EntityArrayResponseType = HttpResponse<IPlan[]>;

@Injectable({ providedIn: 'root' })
export class PricingService {
  public resourceUrl = SERVER_API_URL + 'api/plans';

  constructor(protected http: HttpClient) {}

  get(): Observable<EntityArrayResponseType> {
    return this.http.get<IPlan[]>(`${this.resourceUrl}`, { observe: 'response' });
  }
}
