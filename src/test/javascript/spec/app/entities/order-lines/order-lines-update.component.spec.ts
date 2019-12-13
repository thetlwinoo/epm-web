import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { OrderLinesUpdateComponent } from 'app/entities/order-lines/order-lines-update.component';
import { OrderLinesService } from 'app/entities/order-lines/order-lines.service';
import { OrderLines } from 'app/shared/model/order-lines.model';

describe('Component Tests', () => {
  describe('OrderLines Management Update Component', () => {
    let comp: OrderLinesUpdateComponent;
    let fixture: ComponentFixture<OrderLinesUpdateComponent>;
    let service: OrderLinesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [OrderLinesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrderLinesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderLinesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderLinesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderLines(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderLines();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
