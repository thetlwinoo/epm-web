import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PurchaseOrdersUpdateComponent } from 'app/entities/purchase-orders/purchase-orders-update.component';
import { PurchaseOrdersService } from 'app/entities/purchase-orders/purchase-orders.service';
import { PurchaseOrders } from 'app/shared/model/purchase-orders.model';

describe('Component Tests', () => {
  describe('PurchaseOrders Management Update Component', () => {
    let comp: PurchaseOrdersUpdateComponent;
    let fixture: ComponentFixture<PurchaseOrdersUpdateComponent>;
    let service: PurchaseOrdersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PurchaseOrdersUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PurchaseOrdersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PurchaseOrdersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PurchaseOrdersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PurchaseOrders(123);
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
        const entity = new PurchaseOrders();
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
