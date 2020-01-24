import { Component, OnInit } from '@angular/core';
import { TierType } from 'app/core';

@Component({
  selector: 'jhi-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['pricing.component.scss']
})
export class PricingComponent implements OnInit {
  tierType = TierType;

  constructor() {}

  ngOnInit() {}
}
