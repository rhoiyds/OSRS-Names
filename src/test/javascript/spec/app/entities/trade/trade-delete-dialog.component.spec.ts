/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OsrsnamesTestModule } from '../../../test.module';
import { TradeDeleteDialogComponent } from 'app/entities/trade/trade-delete-dialog.component';
import { TradeService } from 'app/entities/trade/trade.service';

describe('Component Tests', () => {
  describe('Trade Management Delete Component', () => {
    let comp: TradeDeleteDialogComponent;
    let fixture: ComponentFixture<TradeDeleteDialogComponent>;
    let service: TradeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OsrsnamesTestModule],
        declarations: [TradeDeleteDialogComponent]
      })
        .overrideTemplate(TradeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TradeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TradeService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
