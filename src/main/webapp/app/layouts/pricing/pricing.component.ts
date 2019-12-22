import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['pricing.component.scss']
})
export class PricingComponent implements OnInit {
  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.fragment.subscribe(f => {
      const element = document.querySelector('#' + f);
      if (element) element.scrollIntoView();
    });
  }
}
