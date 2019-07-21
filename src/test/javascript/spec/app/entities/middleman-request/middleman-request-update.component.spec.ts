/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { RsnsalesTestModule } from '../../../test.module';
import { MiddlemanRequestUpdateComponent } from 'app/entities/middleman-request/middleman-request-update.component';
import { MiddlemanRequestService } from 'app/entities/middleman-request/middleman-request.service';
import { MiddlemanRequest } from 'app/shared/model/middleman-request.model';

describe('Component Tests', () => {
  describe('MiddlemanRequest Management Update Component', () => {
    let comp: MiddlemanRequestUpdateComponent;
    let fixture: ComponentFixture<MiddlemanRequestUpdateComponent>;
    let service: MiddlemanRequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RsnsalesTestModule],
        declarations: [MiddlemanRequestUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MiddlemanRequestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MiddlemanRequestUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MiddlemanRequestService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MiddlemanRequest(123);
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
        const entity = new MiddlemanRequest();
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
