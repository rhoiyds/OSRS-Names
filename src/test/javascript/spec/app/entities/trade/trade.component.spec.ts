/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RsnsalesTestModule } from '../../../test.module';
import { TradeComponent } from 'app/entities/trade/trade.component';
import { TradeService } from 'app/entities/trade/trade.service';
import { Trade } from 'app/shared/model/trade.model';

describe('Component Tests', () => {
  describe('Trade Management Component', () => {
    let comp: TradeComponent;
    let fixture: ComponentFixture<TradeComponent>;
    let service: TradeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RsnsalesTestModule],
        declarations: [TradeComponent],
        providers: []
      })
        .overrideTemplate(TradeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TradeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TradeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Trade(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.trades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
