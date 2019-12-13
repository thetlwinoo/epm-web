import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { MaterialsUpdateComponent } from 'app/entities/materials/materials-update.component';
import { MaterialsService } from 'app/entities/materials/materials.service';
import { Materials } from 'app/shared/model/materials.model';

describe('Component Tests', () => {
  describe('Materials Management Update Component', () => {
    let comp: MaterialsUpdateComponent;
    let fixture: ComponentFixture<MaterialsUpdateComponent>;
    let service: MaterialsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [MaterialsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MaterialsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MaterialsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaterialsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Materials(123);
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
        const entity = new Materials();
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
