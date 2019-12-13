import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PackageTypesUpdateComponent } from 'app/entities/package-types/package-types-update.component';
import { PackageTypesService } from 'app/entities/package-types/package-types.service';
import { PackageTypes } from 'app/shared/model/package-types.model';

describe('Component Tests', () => {
  describe('PackageTypes Management Update Component', () => {
    let comp: PackageTypesUpdateComponent;
    let fixture: ComponentFixture<PackageTypesUpdateComponent>;
    let service: PackageTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PackageTypesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PackageTypesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PackageTypesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PackageTypesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PackageTypes(123);
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
        const entity = new PackageTypes();
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
