import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { StockItemTempDetailComponent } from 'app/entities/stock-item-temp/stock-item-temp-detail.component';
import { StockItemTemp } from 'app/shared/model/stock-item-temp.model';

describe('Component Tests', () => {
  describe('StockItemTemp Management Detail Component', () => {
    let comp: StockItemTempDetailComponent;
    let fixture: ComponentFixture<StockItemTempDetailComponent>;
    const route = ({ data: of({ stockItemTemp: new StockItemTemp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StockItemTempDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockItemTempDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockItemTempDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stockItemTemp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
