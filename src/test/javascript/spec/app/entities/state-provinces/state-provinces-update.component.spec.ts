import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { StateProvincesUpdateComponent } from 'app/entities/state-provinces/state-provinces-update.component';
import { StateProvincesService } from 'app/entities/state-provinces/state-provinces.service';
import { StateProvinces } from 'app/shared/model/state-provinces.model';

describe('Component Tests', () => {
  describe('StateProvinces Management Update Component', () => {
    let comp: StateProvincesUpdateComponent;
    let fixture: ComponentFixture<StateProvincesUpdateComponent>;
    let service: StateProvincesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StateProvincesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StateProvincesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StateProvincesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StateProvincesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StateProvinces(123);
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
        const entity = new StateProvinces();
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
