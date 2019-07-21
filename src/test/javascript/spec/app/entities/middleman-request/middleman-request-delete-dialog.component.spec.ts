/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RsnsalesTestModule } from '../../../test.module';
import { MiddlemanRequestDeleteDialogComponent } from 'app/entities/middleman-request/middleman-request-delete-dialog.component';
import { MiddlemanRequestService } from 'app/entities/middleman-request/middleman-request.service';

describe('Component Tests', () => {
  describe('MiddlemanRequest Management Delete Component', () => {
    let comp: MiddlemanRequestDeleteDialogComponent;
    let fixture: ComponentFixture<MiddlemanRequestDeleteDialogComponent>;
    let service: MiddlemanRequestService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RsnsalesTestModule],
        declarations: [MiddlemanRequestDeleteDialogComponent]
      })
        .overrideTemplate(MiddlemanRequestDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MiddlemanRequestDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MiddlemanRequestService);
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
