import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMiddlemanRequest } from 'app/shared/model/middleman-request.model';

@Component({
  selector: 'jhi-middleman-request-detail',
  templateUrl: './middleman-request-detail.component.html'
})
export class MiddlemanRequestDetailComponent implements OnInit {
  middlemanRequest: IMiddlemanRequest;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ middlemanRequest }) => {
      this.middlemanRequest = middlemanRequest;
    });
  }

  previousState() {
    window.history.back();
  }
}
