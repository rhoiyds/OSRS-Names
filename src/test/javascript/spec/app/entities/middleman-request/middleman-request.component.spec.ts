/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RsnsalesTestModule } from '../../../test.module';
import { MiddlemanRequestComponent } from 'app/entities/middleman-request/middleman-request.component';
import { MiddlemanRequestService } from 'app/entities/middleman-request/middleman-request.service';
import { MiddlemanRequest } from 'app/shared/model/middleman-request.model';

describe('Component Tests', () => {
  describe('MiddlemanRequest Management Component', () => {
    let comp: MiddlemanRequestComponent;
    let fixture: ComponentFixture<MiddlemanRequestComponent>;
    let service: MiddlemanRequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RsnsalesTestModule],
        declarations: [MiddlemanRequestComponent],
        providers: []
      })
        .overrideTemplate(MiddlemanRequestComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MiddlemanRequestComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MiddlemanRequestService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MiddlemanRequest(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.middlemanRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
