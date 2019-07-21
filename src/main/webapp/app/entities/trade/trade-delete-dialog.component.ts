import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrade } from 'app/shared/model/trade.model';
import { TradeService } from './trade.service';

@Component({
  selector: 'jhi-trade-delete-dialog',
  templateUrl: './trade-delete-dialog.component.html'
})
export class TradeDeleteDialogComponent {
  trade: ITrade;

  constructor(protected tradeService: TradeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tradeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tradeListModification',
        content: 'Deleted an trade'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-trade-delete-popup',
  template: ''
})
export class TradeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ trade }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TradeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.trade = trade;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/trade', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/trade', { outlets: { popup: null } }]);
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
