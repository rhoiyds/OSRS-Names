import { Component, OnInit, AfterViewInit, ViewChild, ElementRef, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PaymentService } from 'app/entities/payment';
import { Payment } from 'app/shared/model/payment.model';

declare var paypal;

@Component({
  selector: 'jhi-paypal-modal',
  templateUrl: './paypal-modal.component.html'
})
export class PayPalModalComponent implements OnInit, AfterViewInit {
  @ViewChild('paypal', { static: true }) paypalElement: ElementRef;

  selectedAmount: string;
  title: string;
  successfullTransaction = false;

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
          return actions.order.capture().then(details => {
            this.paymentService.create({ ...new Payment(), orderId: data.orderID }).subscribe(payment => {
              this.successfullTransaction = true;
              return payment;
            });
          });
        }
      })
      .render(this.paypalElement.nativeElement);
  }

  clear() {
    this.activeModal.close();
  }
}
