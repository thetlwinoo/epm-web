import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductBrandUpdateComponent } from 'app/entities/product-brand/product-brand-update.component';
import { ProductBrandService } from 'app/entities/product-brand/product-brand.service';
import { ProductBrand } from 'app/shared/model/product-brand.model';

describe('Component Tests', () => {
  describe('ProductBrand Management Update Component', () => {
    let comp: ProductBrandUpdateComponent;
    let fixture: ComponentFixture<ProductBrandUpdateComponent>;
    let service: ProductBrandService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductBrandUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductBrandUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductBrandUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductBrandService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductBrand(123);
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
        const entity = new ProductBrand();
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
