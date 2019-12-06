import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IOffer, Offer } from 'app/shared/model/offer.model';
import { OfferService } from './offer.service';
import { IUser, UserService } from 'app/core';
import { IListing, Listing } from 'app/shared/model/listing.model';
import { ListingService } from 'app/entities/listing';
import { filter, map } from 'rxjs/operators';

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
    description: [],
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
      this.getListingForOffer(offer);
    });
  }
  getListingForOffer(offer: IOffer) {
    if (!offer.listing) {
      this.listingService
        .find(this.activatedRoute.snapshot.queryParams.listingId)
        .pipe(
          filter((response: HttpResponse<Listing>) => response.ok),
          map((listing: HttpResponse<Listing>) => listing.body)
        )
        .subscribe(listing => {
          this.editForm.patchValue({ listing });
        });
    }
  }

  updateForm(offer) {
    this.editForm.patchValue({
      id: offer.id,
      description: offer.description,
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
      description: this.editForm.get(['description']).value,
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
}
