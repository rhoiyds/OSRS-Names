import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IComment } from 'app/shared/model/comment.model';
import { AccountService } from 'app/core';
import { CommentService } from './comment.service';

@Component({
  selector: 'jhi-comment',
  templateUrl: './comment.component.html'
})
export class CommentComponent implements OnInit, OnDestroy {
  comments: IComment[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected commentService: CommentService,
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
      this.commentService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IComment[]>) => res.ok),
          map((res: HttpResponse<IComment[]>) => res.body)
        )
        .subscribe((res: IComment[]) => (this.comments = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.commentService
      .query()
      .pipe(
        filter((res: HttpResponse<IComment[]>) => res.ok),
        map((res: HttpResponse<IComment[]>) => res.body)
      )
      .subscribe(
        (res: IComment[]) => {
          this.comments = res;
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
    this.registerChangeInComments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IComment) {
    return item.id;
  }

  registerChangeInComments() {
    this.eventSubscriber = this.eventManager.subscribe('commentListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
