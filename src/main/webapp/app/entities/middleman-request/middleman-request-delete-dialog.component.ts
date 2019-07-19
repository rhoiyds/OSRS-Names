import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMiddlemanRequest } from 'app/shared/model/middleman-request.model';
import { MiddlemanRequestService } from './middleman-request.service';

@Component({
  selector: 'jhi-middleman-request-delete-dialog',
  templateUrl: './middleman-request-delete-dialog.component.html'
})
export class MiddlemanRequestDeleteDialogComponent {
  middlemanRequest: IMiddlemanRequest;

  constructor(
    protected middlemanRequestService: MiddlemanRequestService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.middlemanRequestService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'middlemanRequestListModification',
        content: 'Deleted an middlemanRequest'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-middleman-request-delete-popup',
  template: ''
})
export class MiddlemanRequestDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ middlemanRequest }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MiddlemanRequestDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.middlemanRequest = middlemanRequest;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/middleman-request', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/middleman-request', { outlets: { popup: null } }]);
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
