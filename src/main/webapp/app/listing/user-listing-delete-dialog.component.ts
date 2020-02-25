import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IListing } from 'app/shared/model/listing.model';
import { UserListingService } from './user-listing.service';

@Component({
  selector: 'jhi-user-listing-delete-dialog',
  templateUrl: './user-listing-delete-dialog.component.html'
})
export class UserListingDeleteDialogComponent {
  listing: IListing;

  constructor(
    protected listingService: UserListingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    private router: Router
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.listingService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'listingListModification',
        content: 'Deleted a listing'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-listing-delete-popup',
  template: ''
})
export class UserListingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ listing }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserListingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.listing = listing;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/']);
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
