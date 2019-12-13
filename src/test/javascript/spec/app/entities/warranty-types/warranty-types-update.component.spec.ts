import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { WarrantyTypesUpdateComponent } from 'app/entities/warranty-types/warranty-types-update.component';
import { WarrantyTypesService } from 'app/entities/warranty-types/warranty-types.service';
import { WarrantyTypes } from 'app/shared/model/warranty-types.model';

describe('Component Tests', () => {
  describe('WarrantyTypes Management Update Component', () => {
    let comp: WarrantyTypesUpdateComponent;
    let fixture: ComponentFixture<WarrantyTypesUpdateComponent>;
    let service: WarrantyTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [WarrantyTypesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WarrantyTypesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WarrantyTypesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WarrantyTypesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WarrantyTypes(123);
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
        const entity = new WarrantyTypes();
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
