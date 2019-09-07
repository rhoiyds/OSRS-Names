import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOffer } from 'app/shared/model/offer.model';
import { AccountService } from 'app/core';
import { Subscription } from 'rxjs';
import { OfferService } from 'app/entities/offer';
import { TradeService } from 'app/entities/trade';
import { Trade, TradeStatusType } from 'app/shared/model/trade.model';

@Component({
  selector: 'jhi-listing-offer',
  templateUrl: './listing-offer.component.html'
})
export class ListingOfferComponent implements OnInit, OnDestroy {
  @Input() offer: IOffer;
  eventSubscriber: Subscription;

  constructor(
    protected offerService: OfferService,
    protected tradeService: TradeService,
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

  loadAll() {}

  onAcceptOfferClick() {
    this.tradeService.create(new Trade(null, TradeStatusType.AWAITING_MIDDLEMAN, this.offer)).subscribe(data => {
      console.log(data);
    });
  }

  registerChangeInOffer() {
    this.eventSubscriber = this.eventManager.subscribe('offerListModification', response => this.loadAll());
  }
}
