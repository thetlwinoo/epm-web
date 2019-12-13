import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CustomerCategoriesUpdateComponent } from 'app/entities/customer-categories/customer-categories-update.component';
import { CustomerCategoriesService } from 'app/entities/customer-categories/customer-categories.service';
import { CustomerCategories } from 'app/shared/model/customer-categories.model';

describe('Component Tests', () => {
  describe('CustomerCategories Management Update Component', () => {
    let comp: CustomerCategoriesUpdateComponent;
    let fixture: ComponentFixture<CustomerCategoriesUpdateComponent>;
    let service: CustomerCategoriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CustomerCategoriesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CustomerCategoriesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerCategoriesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerCategoriesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerCategories(123);
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
        const entity = new CustomerCategories();
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
