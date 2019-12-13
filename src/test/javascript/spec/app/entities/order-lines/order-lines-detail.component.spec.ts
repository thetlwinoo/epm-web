import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { OrderLinesDetailComponent } from 'app/entities/order-lines/order-lines-detail.component';
import { OrderLines } from 'app/shared/model/order-lines.model';

describe('Component Tests', () => {
  describe('OrderLines Management Detail Component', () => {
    let comp: OrderLinesDetailComponent;
    let fixture: ComponentFixture<OrderLinesDetailComponent>;
    const route = ({ data: of({ orderLines: new OrderLines(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [OrderLinesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrderLinesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderLinesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderLines).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
