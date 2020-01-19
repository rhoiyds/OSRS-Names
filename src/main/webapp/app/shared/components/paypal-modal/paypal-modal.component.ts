import { Component, OnInit, AfterViewInit, ViewChild, ElementRef, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

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

  constructor(protected activatedRoute: ActivatedRoute, protected http: HttpClient, protected activeModal: NgbActiveModal) {}

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
            const obj = {
              id: data.orderID
            };
            this.http.post<string>('/api/paypal-transaction-complete', obj, { observe: 'response' }).subscribe(order => {
              this.successfullTransaction = true;
              return order;
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
