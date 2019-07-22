import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IComment, Comment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { IUser, UserService } from 'app/core';
import { ITrade } from 'app/shared/model/trade.model';
import { TradeService } from 'app/entities/trade';
import { IOffer } from 'app/shared/model/offer.model';
import { OfferService } from 'app/entities/offer';
import { IMiddlemanRequest } from 'app/shared/model/middleman-request.model';
import { MiddlemanRequestService } from 'app/entities/middleman-request';

@Component({
  selector: 'jhi-comment-update',
  templateUrl: './comment-update.component.html'
})
export class CommentUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  trades: ITrade[];

  offers: IOffer[];

  middlemanrequests: IMiddlemanRequest[];

  editForm = this.fb.group({
    id: [],
    comment: [null, [Validators.required]],
    trade: [],
    offer: [],
    middlemanRequest: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected commentService: CommentService,
    protected userService: UserService,
    protected tradeService: TradeService,
    protected offerService: OfferService,
    protected middlemanRequestService: MiddlemanRequestService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ comment }) => {
      this.updateForm(comment);
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
  }

  updateForm(comment: IComment) {
    this.editForm.patchValue({
      id: comment.id,
      timestamp: comment.timestamp != null ? comment.timestamp.format(DATE_TIME_FORMAT) : null,
      comment: comment.comment,
      owner: comment.owner,
      trade: comment.trade,
      offer: comment.offer,
      middlemanRequest: comment.middlemanRequest
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const comment = this.createFromForm();
    if (comment.id !== undefined) {
      this.subscribeToSaveResponse(this.commentService.update(comment));
    } else {
      this.subscribeToSaveResponse(this.commentService.create(comment));
    }
  }

  private createFromForm(): IComment {
    return {
      ...new Comment(),
      id: this.editForm.get(['id']).value,
      comment: this.editForm.get(['comment']).value,
      trade: this.editForm.get(['trade']).value,
      offer: this.editForm.get(['offer']).value,
      middlemanRequest: this.editForm.get(['middlemanRequest']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>) {
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

  trackOfferById(index: number, item: IOffer) {
    return item.id;
  }

  trackMiddlemanRequestById(index: number, item: IMiddlemanRequest) {
    return item.id;
  }
}
