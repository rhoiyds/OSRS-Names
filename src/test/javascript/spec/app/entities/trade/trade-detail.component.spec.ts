/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RsnsalesTestModule } from '../../../test.module';
import { TradeDetailComponent } from 'app/entities/trade/trade-detail.component';
import { Trade } from 'app/shared/model/trade.model';

describe('Component Tests', () => {
  describe('Trade Management Detail Component', () => {
    let comp: TradeDetailComponent;
    let fixture: ComponentFixture<TradeDetailComponent>;
    const route = ({ data: of({ trade: new Trade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RsnsalesTestModule],
        declarations: [TradeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TradeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TradeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
