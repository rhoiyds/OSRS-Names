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
import { IUser, UserService } from 'app/core';
import { IListing } from 'app/shared/model/listing.model';
import { ListingService } from 'app/entities/listing';

@Component({
  selector: 'jhi-offer-update',
  templateUrl: './offer-update.component.html'
})
export class OfferUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  listings: IListing[];

  editForm = this.fb.group({
    id: [],
    timestamp: [null, [Validators.required]],
    description: [],
    status: [],
    owner: [null, Validators.required],
    listing: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected offerService: OfferService,
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
      status: offer.status,
      owner: offer.owner,
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
      status: this.editForm.get(['status']).value,
      owner: this.editForm.get(['owner']).value,
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackListingById(index: number, item: IListing) {
    return item.id;
  }
}
