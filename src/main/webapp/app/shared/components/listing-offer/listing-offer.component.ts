import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOffer, OfferStatus } from 'app/shared/model/offer.model';
import { IComment } from 'app/shared/model/comment.model';

import { AccountService } from 'app/core';
import { Subscription } from 'rxjs';
import { OfferService } from 'app/entities/offer';
import { CommentService } from 'app/entities/comment';
import { TradeService } from 'app/entities/trade';
import { ITrade, TradeStatus } from 'app/shared/model/trade.model';

@Component({
  selector: 'jhi-listing-offer',
  templateUrl: './listing-offer.component.html',
  styleUrls: ['./listing-offer.component.scss']
})
export class ListingOfferComponent implements OnInit, OnDestroy {
  @Input() offer: IOffer;
  @Input() canAccept: boolean;
  comments: IComment[] = [];
  trade: ITrade;
  newCommentText = '';
  currentAccount: any;
  eventSubscriber: Subscription;
  offerStatus = OfferStatus;
  tradeStatus = TradeStatus;

  constructor(
    protected offerService: OfferService,
    protected commentService: CommentService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService,
    protected tradeService: TradeService
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
    const criteria = {
      'offerId.equals': this.offer.id
    };
    this.commentService.query(criteria).subscribe(response => {
      this.comments = response.body;
    });
    if (this.offer.status === OfferStatus.ACCEPTED) {
      this.tradeService.query(criteria).subscribe(response => {
        this.trade = response.body[0];
      });
    }
  }

  onChangeOfferClick(offerStatus: OfferStatus) {
    this.offer.status = offerStatus;
    this.offerService.update(this.offer).subscribe(response => {
      this.loadAll();
    });
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
        this.comments = this.comments.concat(response.body);
        this.newCommentText = '';
      });
  }

  isNotYetConfirmed() {
    return (
      (this.trade && (this.trade.listingOwnerStatus === TradeStatus.PENDING && this.currentAccount.id === this.offer.listing.owner.id)) ||
      (this.trade.offerOwnerStatus === TradeStatus.PENDING && this.currentAccount.id === this.offer.owner.id)
    );
  }

  onChangeTradeStatusClick(tradeStatus: TradeStatus) {
    console.log(tradeStatus);
  }
}
