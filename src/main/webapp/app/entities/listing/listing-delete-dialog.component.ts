import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IListing } from 'app/shared/model/listing.model';
import { ListingService } from './listing.service';

@Component({
  selector: 'jhi-listing-delete-dialog',
  templateUrl: './listing-delete-dialog.component.html'
})
export class ListingDeleteDialogComponent {
  listing: IListing;

  constructor(protected listingService: ListingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.listingService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'listingListModification',
        content: 'Deleted an listing'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-listing-delete-popup',
  template: ''
})
export class ListingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ listing }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ListingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.listing = listing;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/listing', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/listing', { outlets: { popup: null } }]);
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
