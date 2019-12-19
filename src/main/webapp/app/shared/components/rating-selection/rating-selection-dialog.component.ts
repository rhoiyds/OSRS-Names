import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OfferService } from 'app/entities/offer';
import { ITrade } from 'app/shared/model/trade.model';

@Component({
  selector: 'jhi-trade-rating-dialog',
  templateUrl: './rating-selection-dialog.component.html',
  styleUrls: ['./rating-selection-dialog.component.scss']
})
export class RatingSelectionDialogComponent {
  trade: ITrade;
  message = '';
  rating: number;

  constructor(public activeModal: NgbActiveModal, protected eventManager: JhiEventManager, protected offerService: OfferService) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  setRating(rating) {
    this.rating = rating;
  }

  confirmTrade() {
    // this.offerService
    //   .create({
    //     ...new Offer(),
    //     description: this.message,
    //     listing: this.listing
    //   })
    //   .subscribe(response => {
    //     this.eventManager.broadcast({
    //       name: 'listingModification',
    //       content: 'Created an offer'
    //     });
    //     this.activeModal.dismiss(true);
    //   });
  }
}
