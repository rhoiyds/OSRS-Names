import { Component, OnInit, AfterViewInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { NgbActiveModal, NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PaymentService } from 'app/entities/payment';
import { Payment } from 'app/shared/model/payment.model';
import { Plan } from 'app/shared/model/plan.model';
import { AccountService } from 'app/core';

declare var paypal;

@Component({
  selector: 'jhi-paypal-popup',
  template: ''
})
export class PayPalPopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ plan }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PayPalDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.plan = plan;
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
}

@Component({
  selector: 'jhi-paypal-dialog',
  templateUrl: './paypal-dialog.component.html',
  styleUrls: ['./paypal-dialog.component.scss']
})
export class PayPalDialogComponent implements OnInit, AfterViewInit {
  @ViewChild('paypal', { static: true }) paypalElement: ElementRef;

  plan: Plan;
  transactionComplete = false;
  transactionSuccessful = false;
  processing = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected http: HttpClient,
    protected activeModal: NgbActiveModal,
    protected paymentService: PaymentService,
    protected accountService: AccountService
  ) {}

  ngOnInit() {}

  ngAfterViewInit() {
    paypal
      .Buttons({
        createSubscription: (data, actions) => {
          return actions.subscription.create({
            plan_id: this.plan.id
          });
        },
        onApprove: (data, actions) => {
          this.processing = true;
          this.paymentService
            .create({ ...new Payment(), orderId: data.orderID, subscriptionId: data.subscriptionID })
            .subscribe(payment => {
              this.transactionComplete = true;
              this.transactionSuccessful = true;
              this.processing = false;
              this.accountService.identity(true);
              return payment;
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
