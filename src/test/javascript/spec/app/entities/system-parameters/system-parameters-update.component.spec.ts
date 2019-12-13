import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { SystemParametersUpdateComponent } from 'app/entities/system-parameters/system-parameters-update.component';
import { SystemParametersService } from 'app/entities/system-parameters/system-parameters.service';
import { SystemParameters } from 'app/shared/model/system-parameters.model';

describe('Component Tests', () => {
  describe('SystemParameters Management Update Component', () => {
    let comp: SystemParametersUpdateComponent;
    let fixture: ComponentFixture<SystemParametersUpdateComponent>;
    let service: SystemParametersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SystemParametersUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SystemParametersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SystemParametersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemParametersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SystemParameters(123);
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
        const entity = new SystemParameters();
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
