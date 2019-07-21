import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMiddlemanRequest } from 'app/shared/model/middleman-request.model';
import { AccountService } from 'app/core';
import { MiddlemanRequestService } from './middleman-request.service';

@Component({
  selector: 'jhi-middleman-request',
  templateUrl: './middleman-request.component.html'
})
export class MiddlemanRequestComponent implements OnInit, OnDestroy {
  middlemanRequests: IMiddlemanRequest[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected middlemanRequestService: MiddlemanRequestService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.middlemanRequestService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IMiddlemanRequest[]>) => res.ok),
          map((res: HttpResponse<IMiddlemanRequest[]>) => res.body)
        )
        .subscribe((res: IMiddlemanRequest[]) => (this.middlemanRequests = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.middlemanRequestService
      .query()
      .pipe(
        filter((res: HttpResponse<IMiddlemanRequest[]>) => res.ok),
        map((res: HttpResponse<IMiddlemanRequest[]>) => res.body)
      )
      .subscribe(
        (res: IMiddlemanRequest[]) => {
          this.middlemanRequests = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMiddlemanRequests();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMiddlemanRequest) {
    return item.id;
  }

  registerChangeInMiddlemanRequests() {
    this.eventSubscriber = this.eventManager.subscribe('middlemanRequestListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
