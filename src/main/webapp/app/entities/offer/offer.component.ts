import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOffer } from 'app/shared/model/offer.model';
import { AccountService } from 'app/core';
import { OfferService } from './offer.service';

@Component({
  selector: 'jhi-offer',
  templateUrl: './offer.component.html'
})
export class OfferComponent implements OnInit, OnDestroy {
  offers: IOffer[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected offerService: OfferService,
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
      this.offerService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IOffer[]>) => res.ok),
          map((res: HttpResponse<IOffer[]>) => res.body)
        )
        .subscribe((res: IOffer[]) => (this.offers = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.offerService
      .query()
      .pipe(
        filter((res: HttpResponse<IOffer[]>) => res.ok),
        map((res: HttpResponse<IOffer[]>) => res.body)
      )
      .subscribe(
        (res: IOffer[]) => {
          this.offers = res;
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
    this.registerChangeInOffers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOffer) {
    return item.id;
  }

  registerChangeInOffers() {
    this.eventSubscriber = this.eventManager.subscribe('offerListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
