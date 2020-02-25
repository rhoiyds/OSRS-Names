import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IListing } from 'app/shared/model/listing.model';
import { UserListingService } from './user-listing.service';
import { OfferService } from 'app/entities/offer';
import { Offer } from 'app/shared/model/offer.model';

@Component({
  selector: 'jhi-user-listing-offer-dialog',
  templateUrl: './user-listing-offer-dialog.component.html'
})
export class UserListingOfferDialogComponent {
  listing: IListing;
  message = '';

  constructor(public activeModal: NgbActiveModal, protected eventManager: JhiEventManager, protected offerService: OfferService) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmOffer() {
    this.offerService
      .create({
        ...new Offer(),
        description: this.message,
        listing: this.listing
      })
      .subscribe(response => {
        this.eventManager.broadcast({
          name: 'listingModification',
          content: 'Created an offer'
        });
        this.activeModal.dismiss(true);
      });
  }
}

@Component({
  selector: 'jhi-user-listing-offer-popup',
  template: ''
})
export class UserListingOfferPopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ listing }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserListingOfferDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.listing = listing;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([`/userlisting/${listing.id}/view`, { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([`/userlisting/${listing.id}/view`, { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
