import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITrade, Trade } from 'app/shared/model/trade.model';
import { TradeService } from './trade.service';
import { IOffer } from 'app/shared/model/offer.model';
import { OfferService } from 'app/entities/offer';
import { IMiddlemanRequest } from 'app/shared/model/middleman-request.model';
import { MiddlemanRequestService } from 'app/entities/middleman-request';
import { IRating } from 'app/shared/model/rating.model';
import { RatingService } from 'app/entities/rating';

@Component({
  selector: 'jhi-trade-update',
  templateUrl: './trade-update.component.html'
})
export class TradeUpdateComponent implements OnInit {
  isSaving: boolean;

  offers: IOffer[];

  middlemanrequests: IMiddlemanRequest[];

  ratings: IRating[];

  editForm = this.fb.group({
    id: [],
    status: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tradeService: TradeService,
    protected offerService: OfferService,
    protected middlemanRequestService: MiddlemanRequestService,
    protected ratingService: RatingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ trade }) => {
      this.updateForm(trade);
    });
    this.offerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOffer[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOffer[]>) => response.body)
      )
      .subscribe((res: IOffer[]) => (this.offers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.middlemanRequestService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMiddlemanRequest[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMiddlemanRequest[]>) => response.body)
      )
      .subscribe((res: IMiddlemanRequest[]) => (this.middlemanrequests = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.ratingService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRating[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRating[]>) => response.body)
      )
      .subscribe((res: IRating[]) => (this.ratings = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(trade: ITrade) {
    this.editForm.patchValue({
      id: trade.id,
      status: trade.status
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const trade = this.createFromForm();
    if (trade.id !== undefined) {
      this.subscribeToSaveResponse(this.tradeService.update(trade));
    } else {
      this.subscribeToSaveResponse(this.tradeService.create(trade));
    }
  }

  private createFromForm(): ITrade {
    return {
      ...new Trade(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrade>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOfferById(index: number, item: IOffer) {
    return item.id;
  }

  trackMiddlemanRequestById(index: number, item: IMiddlemanRequest) {
    return item.id;
  }

  trackRatingById(index: number, item: IRating) {
    return item.id;
  }
}
