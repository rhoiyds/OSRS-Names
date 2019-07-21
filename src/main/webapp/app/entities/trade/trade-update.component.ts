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

@Component({
  selector: 'jhi-trade-update',
  templateUrl: './trade-update.component.html'
})
export class TradeUpdateComponent implements OnInit {
  isSaving: boolean;

  offers: IOffer[];

  editForm = this.fb.group({
    id: [],
    status: [null, [Validators.required]],
    offer: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tradeService: TradeService,
    protected offerService: OfferService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ trade }) => {
      this.updateForm(trade);
    });
    this.offerService
      .query({ filter: 'trade-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IOffer[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOffer[]>) => response.body)
      )
      .subscribe(
        (res: IOffer[]) => {
          if (!this.editForm.get('offer').value || !this.editForm.get('offer').value.id) {
            this.offers = res;
          } else {
            this.offerService
              .find(this.editForm.get('offer').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IOffer>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IOffer>) => subResponse.body)
              )
              .subscribe(
                (subRes: IOffer) => (this.offers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(trade: ITrade) {
    this.editForm.patchValue({
      id: trade.id,
      status: trade.status,
      offer: trade.offer
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
      status: this.editForm.get(['status']).value,
      offer: this.editForm.get(['offer']).value
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
}
