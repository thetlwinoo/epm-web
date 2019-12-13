import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { OrderLinesComponent } from 'app/entities/order-lines/order-lines.component';
import { OrderLinesService } from 'app/entities/order-lines/order-lines.service';
import { OrderLines } from 'app/shared/model/order-lines.model';

describe('Component Tests', () => {
  describe('OrderLines Management Component', () => {
    let comp: OrderLinesComponent;
    let fixture: ComponentFixture<OrderLinesComponent>;
    let service: OrderLinesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [OrderLinesComponent],
        providers: []
      })
        .overrideTemplate(OrderLinesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderLinesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderLinesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderLines(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderLines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
