import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ShipMethodUpdateComponent } from 'app/entities/ship-method/ship-method-update.component';
import { ShipMethodService } from 'app/entities/ship-method/ship-method.service';
import { ShipMethod } from 'app/shared/model/ship-method.model';

describe('Component Tests', () => {
  describe('ShipMethod Management Update Component', () => {
    let comp: ShipMethodUpdateComponent;
    let fixture: ComponentFixture<ShipMethodUpdateComponent>;
    let service: ShipMethodService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ShipMethodUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ShipMethodUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShipMethodUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShipMethodService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShipMethod(123);
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
        const entity = new ShipMethod();
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
