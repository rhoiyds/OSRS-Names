import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITrade } from 'app/shared/model/trade.model';
import { AccountService } from 'app/core';
import { TradeService } from './trade.service';

@Component({
  selector: 'jhi-trade',
  templateUrl: './trade.component.html'
})
export class TradeComponent implements OnInit, OnDestroy {
  trades: ITrade[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected tradeService: TradeService,
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
      this.tradeService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ITrade[]>) => res.ok),
          map((res: HttpResponse<ITrade[]>) => res.body)
        )
        .subscribe((res: ITrade[]) => (this.trades = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.tradeService
      .query()
      .pipe(
        filter((res: HttpResponse<ITrade[]>) => res.ok),
        map((res: HttpResponse<ITrade[]>) => res.body)
      )
      .subscribe(
        (res: ITrade[]) => {
          this.trades = res;
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
    this.registerChangeInTrades();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITrade) {
    return item.id;
  }

  registerChangeInTrades() {
    this.eventSubscriber = this.eventManager.subscribe('tradeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
