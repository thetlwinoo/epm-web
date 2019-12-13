import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PurchaseOrderLinesUpdateComponent } from 'app/entities/purchase-order-lines/purchase-order-lines-update.component';
import { PurchaseOrderLinesService } from 'app/entities/purchase-order-lines/purchase-order-lines.service';
import { PurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';

describe('Component Tests', () => {
  describe('PurchaseOrderLines Management Update Component', () => {
    let comp: PurchaseOrderLinesUpdateComponent;
    let fixture: ComponentFixture<PurchaseOrderLinesUpdateComponent>;
    let service: PurchaseOrderLinesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PurchaseOrderLinesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PurchaseOrderLinesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PurchaseOrderLinesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PurchaseOrderLinesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PurchaseOrderLines(123);
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
        const entity = new PurchaseOrderLines();
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
