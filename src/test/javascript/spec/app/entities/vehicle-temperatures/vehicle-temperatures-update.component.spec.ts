import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { VehicleTemperaturesUpdateComponent } from 'app/entities/vehicle-temperatures/vehicle-temperatures-update.component';
import { VehicleTemperaturesService } from 'app/entities/vehicle-temperatures/vehicle-temperatures.service';
import { VehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';

describe('Component Tests', () => {
  describe('VehicleTemperatures Management Update Component', () => {
    let comp: VehicleTemperaturesUpdateComponent;
    let fixture: ComponentFixture<VehicleTemperaturesUpdateComponent>;
    let service: VehicleTemperaturesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [VehicleTemperaturesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VehicleTemperaturesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleTemperaturesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleTemperaturesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VehicleTemperatures(123);
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
        const entity = new VehicleTemperatures();
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
