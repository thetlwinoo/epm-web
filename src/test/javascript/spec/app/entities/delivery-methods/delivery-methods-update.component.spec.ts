import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { DeliveryMethodsUpdateComponent } from 'app/entities/delivery-methods/delivery-methods-update.component';
import { DeliveryMethodsService } from 'app/entities/delivery-methods/delivery-methods.service';
import { DeliveryMethods } from 'app/shared/model/delivery-methods.model';

describe('Component Tests', () => {
  describe('DeliveryMethods Management Update Component', () => {
    let comp: DeliveryMethodsUpdateComponent;
    let fixture: ComponentFixture<DeliveryMethodsUpdateComponent>;
    let service: DeliveryMethodsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [DeliveryMethodsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DeliveryMethodsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeliveryMethodsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeliveryMethodsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeliveryMethods(123);
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
        const entity = new DeliveryMethods();
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
