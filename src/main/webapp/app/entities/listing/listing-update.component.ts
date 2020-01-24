import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IListing, Listing, ListingType } from 'app/shared/model/listing.model';
import { ListingService } from './listing.service';
import { IUser, UserService } from 'app/core';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag';

@Component({
  selector: 'jhi-listing-update',
  templateUrl: './listing-update.component.html'
})
export class ListingUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  tags: ITag[];

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
    rsn: [null, [Validators.required]],
    amount: [],
    description: [],
    tags: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected listingService: ListingService,
    protected userService: UserService,
    protected tagService: TagService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ listing }) => {
      this.updateForm(listing);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tagService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITag[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITag[]>) => response.body)
      )
      .subscribe((res: ITag[]) => (this.tags = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(listing: IListing) {
    this.editForm.patchValue({
      id: listing.id,
      type: listing.type,
      rsn: listing.rsn,
      amount: listing.amount,
      description: listing.description,
      tags: listing.tags
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const listing = this.createFromForm();
    if (listing.id !== undefined) {
      this.subscribeToSaveResponse(this.listingService.update(listing));
    } else {
      this.subscribeToSaveResponse(this.listingService.create(listing));
    }
  }

  private createFromForm(): IListing {
    return {
      ...new Listing(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value,
      rsn: this.editForm.get(['rsn']).value,
      amount: this.editForm.get(['amount']).value,
      description: this.editForm.get(['description']).value,
      tags: this.editForm.get(['tags']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IListing>>) {
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

  trackTagById(index: number, item: ITag) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  getRsnLabel() {
    if (!this.editForm.get(['type']).value) {
      return 'RSN';
    }
    if (this.editForm.get(['type']).value === ListingType.HAVE) {
      return 'What RSN are you selling?';
    }
    return 'What RSN are you buying?';
  }

  getAmountLabel() {
    if (!this.editForm.get(['type']).value) {
      return 'How much GP?';
    }
    if (this.editForm.get(['type']).value === ListingType.HAVE) {
      return 'How much GP will you sell this name for?';
    }
    return 'How much GP will you buy this name for?';
  }

  public onAdding(tag: string) {
    return of(tag.toLowerCase());
  }
}
