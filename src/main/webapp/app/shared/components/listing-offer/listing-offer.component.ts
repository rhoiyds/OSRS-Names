import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOffer } from 'app/shared/model/offer.model';
import { IComment } from 'app/shared/model/comment.model';

import { AccountService } from 'app/core';
import { Subscription } from 'rxjs';
import { OfferService } from 'app/entities/offer';
import { CommentService } from 'app/entities/comment';

@Component({
  selector: 'jhi-listing-offer',
  templateUrl: './listing-offer.component.html',
  styleUrls: ['./listing-offer.component.scss']
})
export class ListingOfferComponent implements OnInit, OnDestroy {
  @Input() offer: IOffer;
  comments: IComment[] = [];
  newCommentText = '';
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected offerService: OfferService,
    protected commentService: CommentService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {}

  ngOnInit() {
    this.loadAll();
    this.registerChangeInOffer();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  loadAll() {
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    let criteria = {
      'offer.equals': this.offer
    };
    this.commentService.query(criteria).subscribe(response => {
      console.log(response);
    });
  }

  onAcceptOfferClick() {
    console.log('Accept');
  }

  onDeclineOfferClick() {
    console.log('Decline');
  }

  registerChangeInOffer() {
    this.eventSubscriber = this.eventManager.subscribe('offerListModification', response => this.loadAll());
  }

  onPostCommentClick() {
    this.commentService
      .create({
        text: this.newCommentText,
        offer: this.offer
      })
      .subscribe(response => {
        this.comments.concat(response.body);
      });
  }
}
