import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['pricing.component.scss']
})
export class PricingComponent implements OnInit {
  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {}
}
