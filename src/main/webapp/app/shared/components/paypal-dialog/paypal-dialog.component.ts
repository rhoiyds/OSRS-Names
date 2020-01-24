import { Component, OnInit, AfterViewInit, ViewChild, ElementRef, TemplateRef, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { NgbActiveModal, NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PaymentService } from 'app/entities/payment';
import { Payment } from 'app/shared/model/payment.model';
import { TierType } from 'app/core';

declare var paypal;

@Component({
  selector: 'jhi-paypal-popup',
  template: ''
})
export class PayPalPopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.params.subscribe(routeParams => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PayPalDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.title = this.getTitleFromTier(routeParams.tier);
        this.ngbModalRef.componentInstance.selectedAmount = this.getAmountFromTier(routeParams.tier);
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pricing', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pricing', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }

  getTitleFromTier(tierType) {
    switch (tierType) {
      case TierType.POWERSELLER:
        return 'Power Seller';
      case TierType.PREMIUM:
        return 'Premium';
      default:
        return '';
    }
  }

  getAmountFromTier(tierType) {
    switch (tierType) {
      case TierType.POWERSELLER:
        return '99.00';
      case TierType.PREMIUM:
        return '49.00';
      default:
        return '0.00';
    }
  }
}

@Component({
  selector: 'jhi-paypal-dialog',
  templateUrl: './paypal-dialog.component.html',
  styleUrls: ['./paypal-dialog.component.scss']
})
export class PayPalDialogComponent implements OnInit, AfterViewInit {
  selectedAmount: string;
  title: string;
  transactionComplete = false;
  transactionSuccessful = false;
  processing = false;
  @ViewChild('paypal', { static: true }) paypalElement: ElementRef;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected http: HttpClient,
    protected activeModal: NgbActiveModal,
    protected paymentService: PaymentService
  ) {}

  ngOnInit() {}

  ngAfterViewInit() {
    paypal
      .Buttons({
        createOrder: (data, actions) => {
          return actions.order.create({
            purchase_units: [
              {
                amount: {
                  value: this.selectedAmount
                }
              }
            ]
          });
        },
        onApprove: (data, actions) => {
          this.processing = true;
          return actions.order.capture().then(details => {
            this.paymentService.create({ ...new Payment(), orderId: data.orderID }).subscribe(payment => {
              this.transactionComplete = true;
              this.transactionSuccessful = true;
              this.processing = false;
              return payment;
            });
          });
        },
        onError: err => {
          this.transactionComplete = true;
          this.processing = false;
          this.transactionSuccessful = false;
          console.log(err);
        }
      })
      .render(this.paypalElement.nativeElement);
  }

  clear() {
    this.activeModal.close();
  }
}
