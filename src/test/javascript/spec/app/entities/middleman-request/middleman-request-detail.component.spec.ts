/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RsnsalesTestModule } from '../../../test.module';
import { MiddlemanRequestDetailComponent } from 'app/entities/middleman-request/middleman-request-detail.component';
import { MiddlemanRequest } from 'app/shared/model/middleman-request.model';

describe('Component Tests', () => {
  describe('MiddlemanRequest Management Detail Component', () => {
    let comp: MiddlemanRequestDetailComponent;
    let fixture: ComponentFixture<MiddlemanRequestDetailComponent>;
    const route = ({ data: of({ middlemanRequest: new MiddlemanRequest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RsnsalesTestModule],
        declarations: [MiddlemanRequestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MiddlemanRequestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MiddlemanRequestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.middlemanRequest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
