import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { IOffer, OfferStatus } from 'app/shared/model/offer.model';
import { IComment } from 'app/shared/model/comment.model';

import { AccountService } from 'app/core';
import { Subscription } from 'rxjs';
import { OfferService } from 'app/entities/offer';
import { CommentService } from 'app/entities/comment';
import { TradeService } from 'app/entities/trade';
import { ITrade, TradeStatus } from 'app/shared/model/trade.model';
import { RatingSelectionDialogComponent } from 'app/shared/components/rating-selection/rating-selection-dialog.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GRAVATAR_AVATAR_PATH, GRAVATAR_BASE_URL, GRAVATAR_PARAMETERS } from 'app/shared/constants/gravatar.constants';

@Component({
  selector: 'jhi-listing-offer',
  templateUrl: './listing-offer.component.html',
  styleUrls: ['./listing-offer.component.scss']
})
export class ListingOfferComponent implements OnInit, OnDestroy {
  DECLINED_STATUS = 'indicated there was a problem with the trade';
  CONFIRMED_STATUS = 'indicated it was a successful trade';
  PENDING_STATUS = 'has yet to confirm the results of the trade';

  @Input() offer: IOffer;
  @Input() canAccept: boolean;
  comments: IComment[] = [];
  trade: ITrade;
  newCommentText = '';
  currentAccount: any;
  eventSubscriber: Subscription;
  offerStatus = OfferStatus;
  tradeStatus = TradeStatus;
  ngbModalRef: NgbModalRef;

  constructor(
    protected offerService: OfferService,
    protected commentService: CommentService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService,
    protected tradeService: TradeService,
    protected modalService: NgbModal
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
      (this.trade.listingOwnerStatus === TradeStatus.PENDING && this.currentAccount.id === this.offer.listing.owner.id) ||
      (this.trade.offerOwnerStatus === TradeStatus.PENDING && this.currentAccount.id === this.offer.owner.id)
    );
  }

  onChangeTradeStatusClick() {
    if (this.isNotYetConfirmed()) {
      this.ngbModalRef = this.modalService.open(RatingSelectionDialogComponent as Component, { size: 'lg', backdrop: 'static' });
      this.ngbModalRef.componentInstance.trade = this.trade;
    }
  }

  getGravatarImageURL(imageUrl) {
    return GRAVATAR_BASE_URL + GRAVATAR_AVATAR_PATH + imageUrl + GRAVATAR_PARAMETERS;
  }

  private getFromStatus(tradeStatus: TradeStatus) {
    if (tradeStatus === TradeStatus.CONFIRMED) {
      return this.CONFIRMED_STATUS;
    }
    if (tradeStatus === TradeStatus.DECLINED) {
      return this.DECLINED_STATUS;
    }
    if (tradeStatus === TradeStatus.PENDING) {
      return this.PENDING_STATUS;
    }
  }
}
