import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { StockItemsDetailComponent } from 'app/entities/stock-items/stock-items-detail.component';
import { StockItems } from 'app/shared/model/stock-items.model';

describe('Component Tests', () => {
  describe('StockItems Management Detail Component', () => {
    let comp: StockItemsDetailComponent;
    let fixture: ComponentFixture<StockItemsDetailComponent>;
    const route = ({ data: of({ stockItems: new StockItems(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StockItemsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockItemsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockItemsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stockItems).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
