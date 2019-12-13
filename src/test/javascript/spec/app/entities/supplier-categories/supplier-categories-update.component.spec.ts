import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { SupplierCategoriesUpdateComponent } from 'app/entities/supplier-categories/supplier-categories-update.component';
import { SupplierCategoriesService } from 'app/entities/supplier-categories/supplier-categories.service';
import { SupplierCategories } from 'app/shared/model/supplier-categories.model';

describe('Component Tests', () => {
  describe('SupplierCategories Management Update Component', () => {
    let comp: SupplierCategoriesUpdateComponent;
    let fixture: ComponentFixture<SupplierCategoriesUpdateComponent>;
    let service: SupplierCategoriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SupplierCategoriesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SupplierCategoriesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierCategoriesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierCategoriesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SupplierCategories(123);
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
        const entity = new SupplierCategories();
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
