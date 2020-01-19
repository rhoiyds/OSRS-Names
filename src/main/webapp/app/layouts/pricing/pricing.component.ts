import { Component, OnInit, AfterViewInit, ViewChild, ElementRef, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PayPalModalComponent } from 'app/shared/components/paypal-modal/paypal-modal.component';

declare var paypal;

@Component({
  selector: 'jhi-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['pricing.component.scss']
})
export class PricingComponent implements OnInit {
  @ViewChild('paypal', { static: true }) paypalElement: ElementRef;

  @ViewChild('templateRef', { static: true, read: TemplateRef }) templateRef: TemplateRef<any>;

  selectedAmount: String;

  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, private modalService: NgbModal) {}

  ngOnInit() {}

  onBuyTierClick(amount, title) {
    this.selectedAmount = amount;
    this.ngbModalRef = this.modalService.open(PayPalModalComponent as Component, { size: 'lg', backdrop: 'static' });
    this.ngbModalRef.componentInstance.selectedAmount = this.selectedAmount;
    this.ngbModalRef.componentInstance.title = title;
  }
}
