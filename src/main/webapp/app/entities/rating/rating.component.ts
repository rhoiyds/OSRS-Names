import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRating } from 'app/shared/model/rating.model';
import { AccountService } from 'app/core';
import { RatingService } from './rating.service';

@Component({
  selector: 'jhi-rating',
  templateUrl: './rating.component.html'
})
export class RatingComponent implements OnInit, OnDestroy {
  ratings: IRating[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected ratingService: RatingService,
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
      this.ratingService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IRating[]>) => res.ok),
          map((res: HttpResponse<IRating[]>) => res.body)
        )
        .subscribe((res: IRating[]) => (this.ratings = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.ratingService
      .query()
      .pipe(
        filter((res: HttpResponse<IRating[]>) => res.ok),
        map((res: HttpResponse<IRating[]>) => res.body)
      )
      .subscribe(
        (res: IRating[]) => {
          this.ratings = res;
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
    this.registerChangeInRatings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRating) {
    return item.id;
  }

  registerChangeInRatings() {
    this.eventSubscriber = this.eventManager.subscribe('ratingListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
