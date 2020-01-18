import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

declare var paypal;

@Component({
  selector: 'jhi-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['pricing.component.scss']
})
export class PricingComponent implements OnInit, AfterViewInit {
  @ViewChild('paypal', { static: true }) paypalElement: ElementRef;

  constructor(protected activatedRoute: ActivatedRoute, private http: HttpClient) {}

  ngOnInit() {}

  ngAfterViewInit() {
    paypal
      .Buttons({
        createOrder: function(data, actions) {
          return actions.order.create({
            purchase_units: [
              {
                amount: {
                  value: '0.01'
                }
              }
            ]
          });
        },
        onApprove: (data, actions) => {
          return actions.order.capture().then(details => {
            console.log('Transaction completed by ' + details.payer.name.given_name);
            const obj = {
              id: data.orderID
            };
            this.http.post<string>('/api/paypal-transaction-complete', obj, { observe: 'response' }).subscribe(data => {
              return data;
            });
          });
        }
      })
      .render(this.paypalElement.nativeElement);
  }
}
