import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IMiddlemanRequest, MiddlemanRequest } from 'app/shared/model/middleman-request.model';
import { MiddlemanRequestService } from './middleman-request.service';
import { ITrade } from 'app/shared/model/trade.model';
import { TradeService } from 'app/entities/trade';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-middleman-request-update',
  templateUrl: './middleman-request-update.component.html'
})
export class MiddlemanRequestUpdateComponent implements OnInit {
  isSaving: boolean;

  trades: ITrade[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    timestamp: [null, [Validators.required]],
    description: [],
    trade: [],
    user: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected middlemanRequestService: MiddlemanRequestService,
    protected tradeService: TradeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ middlemanRequest }) => {
      this.updateForm(middlemanRequest);
    });
    this.tradeService
      .query({ filter: 'middlemanrequest-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ITrade[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITrade[]>) => response.body)
      )
      .subscribe(
        (res: ITrade[]) => {
          if (!this.editForm.get('trade').value || !this.editForm.get('trade').value.id) {
            this.trades = res;
          } else {
            this.tradeService
              .find(this.editForm.get('trade').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ITrade>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ITrade>) => subResponse.body)
              )
              .subscribe(
                (subRes: ITrade) => (this.trades = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(middlemanRequest: IMiddlemanRequest) {
    this.editForm.patchValue({
      id: middlemanRequest.id,
      timestamp: middlemanRequest.timestamp != null ? middlemanRequest.timestamp.format(DATE_TIME_FORMAT) : null,
      description: middlemanRequest.description,
      trade: middlemanRequest.trade,
      user: middlemanRequest.user,
      user: middlemanRequest.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const middlemanRequest = this.createFromForm();
    if (middlemanRequest.id !== undefined) {
      this.subscribeToSaveResponse(this.middlemanRequestService.update(middlemanRequest));
    } else {
      this.subscribeToSaveResponse(this.middlemanRequestService.create(middlemanRequest));
    }
  }

  private createFromForm(): IMiddlemanRequest {
    return {
      ...new MiddlemanRequest(),
      id: this.editForm.get(['id']).value,
      timestamp:
        this.editForm.get(['timestamp']).value != null ? moment(this.editForm.get(['timestamp']).value, DATE_TIME_FORMAT) : undefined,
      description: this.editForm.get(['description']).value,
      trade: this.editForm.get(['trade']).value,
      user: this.editForm.get(['user']).value,
      user: this.editForm.get(['user']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMiddlemanRequest>>) {
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

  trackTradeById(index: number, item: ITrade) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
