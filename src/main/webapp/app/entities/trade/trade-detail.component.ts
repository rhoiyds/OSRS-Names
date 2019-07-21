import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrade } from 'app/shared/model/trade.model';

@Component({
  selector: 'jhi-trade-detail',
  templateUrl: './trade-detail.component.html'
})
export class TradeDetailComponent implements OnInit {
  trade: ITrade;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ trade }) => {
      this.trade = trade;
    });
  }

  previousState() {
    window.history.back();
  }
}
