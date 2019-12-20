import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OfferService } from 'app/entities/offer';
import { ITrade, TradeStatus } from 'app/shared/model/trade.model';
import { TradeService } from 'app/entities/trade';

@Component({
  selector: 'jhi-trade-rating-dialog',
  templateUrl: './rating-selection-dialog.component.html',
  styleUrls: ['./rating-selection-dialog.component.scss']
})
export class RatingSelectionDialogComponent {
  trade: ITrade;
  message = '';
  score: number;
  tradeStatus = TradeStatus;

  constructor(
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    protected offerService: OfferService,
    protected tradeService: TradeService
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  setScore(score) {
    this.score = score;
  }

  confirmTrade(tradeStatus: TradeStatus) {
    this.tradeService
      .rate(this.trade.id, {
        message: this.message,
        score: this.score,
        tradeStatus: tradeStatus
      })
      .subscribe(response => {
        this.eventManager.broadcast({
          name: 'listingModification',
          content: 'Provided rating'
        });
        this.activeModal.dismiss(true);
      });
  }
}
