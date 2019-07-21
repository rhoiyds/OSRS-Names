/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RsnsalesTestModule } from '../../../test.module';
import { ListingDeleteDialogComponent } from 'app/entities/listing/listing-delete-dialog.component';
import { ListingService } from 'app/entities/listing/listing.service';

describe('Component Tests', () => {
  describe('Listing Management Delete Component', () => {
    let comp: ListingDeleteDialogComponent;
    let fixture: ComponentFixture<ListingDeleteDialogComponent>;
    let service: ListingService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RsnsalesTestModule],
        declarations: [ListingDeleteDialogComponent]
      })
        .overrideTemplate(ListingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ListingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ListingService);
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
