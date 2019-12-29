/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { OsrsnamesTestModule } from '../../../test.module';
import { TradeUpdateComponent } from 'app/entities/trade/trade-update.component';
import { TradeService } from 'app/entities/trade/trade.service';
import { Trade } from 'app/shared/model/trade.model';

describe('Component Tests', () => {
  describe('Trade Management Update Component', () => {
    let comp: TradeUpdateComponent;
    let fixture: ComponentFixture<TradeUpdateComponent>;
    let service: TradeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OsrsnamesTestModule],
        declarations: [TradeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TradeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TradeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TradeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Trade(123);
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
        const entity = new Trade();
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
