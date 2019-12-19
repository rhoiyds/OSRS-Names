import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRating, Rating } from 'app/shared/model/rating.model';
import { RatingService } from './rating.service';
import { IUser, UserService } from 'app/core';
import { ITrade } from 'app/shared/model/trade.model';
import { TradeService } from 'app/entities/trade';

@Component({
  selector: 'jhi-rating-update',
  templateUrl: './rating-update.component.html'
})
export class RatingUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  trades: ITrade[];

  editForm = this.fb.group({
    id: [],
    score: [null, [Validators.required, Validators.min(1), Validators.max(5)]],
    message: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(1024)]],
    timestamp: [],
    owner: [],
    recipient: [],
    trade: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ratingService: RatingService,
    protected userService: UserService,
    protected tradeService: TradeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rating }) => {
      this.updateForm(rating);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tradeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITrade[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITrade[]>) => response.body)
      )
      .subscribe((res: ITrade[]) => (this.trades = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(rating: IRating) {
    this.editForm.patchValue({
      id: rating.id,
      score: rating.score,
      message: rating.message,
      timestamp: rating.timestamp != null ? rating.timestamp.format(DATE_TIME_FORMAT) : null,
      owner: rating.owner,
      recipient: rating.recipient,
      trade: rating.trade
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rating = this.createFromForm();
    if (rating.id !== undefined) {
      this.subscribeToSaveResponse(this.ratingService.update(rating));
    } else {
      this.subscribeToSaveResponse(this.ratingService.create(rating));
    }
  }

  private createFromForm(): IRating {
    return {
      ...new Rating(),
      id: this.editForm.get(['id']).value,
      score: this.editForm.get(['score']).value,
      message: this.editForm.get(['message']).value,
      timestamp:
        this.editForm.get(['timestamp']).value != null ? moment(this.editForm.get(['timestamp']).value, DATE_TIME_FORMAT) : undefined,
      owner: this.editForm.get(['owner']).value,
      recipient: this.editForm.get(['recipient']).value,
      trade: this.editForm.get(['trade']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRating>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackTradeById(index: number, item: ITrade) {
    return item.id;
  }
}
