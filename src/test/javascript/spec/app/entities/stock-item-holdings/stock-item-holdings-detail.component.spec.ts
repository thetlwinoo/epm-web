import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { StockItemHoldingsDetailComponent } from 'app/entities/stock-item-holdings/stock-item-holdings-detail.component';
import { StockItemHoldings } from 'app/shared/model/stock-item-holdings.model';

describe('Component Tests', () => {
  describe('StockItemHoldings Management Detail Component', () => {
    let comp: StockItemHoldingsDetailComponent;
    let fixture: ComponentFixture<StockItemHoldingsDetailComponent>;
    const route = ({ data: of({ stockItemHoldings: new StockItemHoldings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StockItemHoldingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockItemHoldingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockItemHoldingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stockItemHoldings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
