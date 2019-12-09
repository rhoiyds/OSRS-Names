import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOffer } from 'app/shared/model/offer.model';
import { AccountService } from 'app/core';
import { Subscription } from 'rxjs';
import { OfferService } from 'app/entities/offer';

@Component({
  selector: 'jhi-listing-offer',
  templateUrl: './listing-offer.component.html'
})
export class ListingOfferComponent implements OnInit, OnDestroy {
  @Input() offer: IOffer;
  eventSubscriber: Subscription;

  constructor(
    protected offerService: OfferService,
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
    console.log('Accept');
  }

  onDeclineOfferClick() {
    console.log('Decline');
  }

  registerChangeInOffer() {
    this.eventSubscriber = this.eventManager.subscribe('offerListModification', response => this.loadAll());
  }
}
