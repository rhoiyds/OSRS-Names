/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OsrsnamesTestModule } from '../../../test.module';
import { ListingDetailComponent } from 'app/entities/listing/listing-detail.component';
import { Listing } from 'app/shared/model/listing.model';

describe('Component Tests', () => {
  describe('Listing Management Detail Component', () => {
    let comp: ListingDetailComponent;
    let fixture: ComponentFixture<ListingDetailComponent>;
    const route = ({ data: of({ listing: new Listing(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OsrsnamesTestModule],
        declarations: [ListingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ListingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ListingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.listing).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
