import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOffer, Offer } from 'app/shared/model/offer.model';
import { OfferService } from './offer.service';
import { ITrade } from 'app/shared/model/trade.model';
import { TradeService } from 'app/entities/trade';
import { IUser, UserService } from 'app/core';
import { IListing } from 'app/shared/model/listing.model';
import { ListingService } from 'app/entities/listing';

@Component({
  selector: 'jhi-offer-update',
  templateUrl: './offer-update.component.html'
})
export class OfferUpdateComponent implements OnInit {
  isSaving: boolean;

  trades: ITrade[];

  users: IUser[];

  listings: IListing[];

  editForm = this.fb.group({
    id: [],
    timestamp: [null, [Validators.required]],
    description: [],
    trade: [],
    user: [],
    listing: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected offerService: OfferService,
    protected tradeService: TradeService,
    protected userService: UserService,
    protected listingService: ListingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ offer }) => {
      this.updateForm(offer);
    });
    this.tradeService
      .query({ filter: 'offer-is-null' })
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
    this.listingService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IListing[]>) => mayBeOk.ok),
        map((response: HttpResponse<IListing[]>) => response.body)
      )
      .subscribe((res: IListing[]) => (this.listings = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(offer: IOffer) {
    this.editForm.patchValue({
      id: offer.id,
      timestamp: offer.timestamp != null ? offer.timestamp.format(DATE_TIME_FORMAT) : null,
      description: offer.description,
      trade: offer.trade,
      user: offer.user,
      listing: offer.listing
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const offer = this.createFromForm();
    if (offer.id !== undefined) {
      this.subscribeToSaveResponse(this.offerService.update(offer));
    } else {
      this.subscribeToSaveResponse(this.offerService.create(offer));
    }
  }

  private createFromForm(): IOffer {
    return {
      ...new Offer(),
      id: this.editForm.get(['id']).value,
      timestamp:
        this.editForm.get(['timestamp']).value != null ? moment(this.editForm.get(['timestamp']).value, DATE_TIME_FORMAT) : undefined,
      description: this.editForm.get(['description']).value,
      trade: this.editForm.get(['trade']).value,
      user: this.editForm.get(['user']).value,
      listing: this.editForm.get(['listing']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffer>>) {
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

  trackListingById(index: number, item: IListing) {
    return item.id;
  }
}
