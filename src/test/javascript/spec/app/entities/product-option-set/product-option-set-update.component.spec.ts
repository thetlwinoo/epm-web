import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductOptionSetUpdateComponent } from 'app/entities/product-option-set/product-option-set-update.component';
import { ProductOptionSetService } from 'app/entities/product-option-set/product-option-set.service';
import { ProductOptionSet } from 'app/shared/model/product-option-set.model';

describe('Component Tests', () => {
  describe('ProductOptionSet Management Update Component', () => {
    let comp: ProductOptionSetUpdateComponent;
    let fixture: ComponentFixture<ProductOptionSetUpdateComponent>;
    let service: ProductOptionSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductOptionSetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductOptionSetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductOptionSetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductOptionSetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductOptionSet(123);
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
        const entity = new ProductOptionSet();
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
