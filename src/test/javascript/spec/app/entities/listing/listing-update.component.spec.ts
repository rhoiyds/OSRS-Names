/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { OsrsnamesTestModule } from '../../../test.module';
import { ListingUpdateComponent } from 'app/entities/listing/listing-update.component';
import { ListingService } from 'app/entities/listing/listing.service';
import { Listing } from 'app/shared/model/listing.model';

describe('Component Tests', () => {
  describe('Listing Management Update Component', () => {
    let comp: ListingUpdateComponent;
    let fixture: ComponentFixture<ListingUpdateComponent>;
    let service: ListingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OsrsnamesTestModule],
        declarations: [ListingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ListingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ListingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ListingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Listing(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Listing();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
