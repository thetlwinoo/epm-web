import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductAttributeUpdateComponent } from 'app/entities/product-attribute/product-attribute-update.component';
import { ProductAttributeService } from 'app/entities/product-attribute/product-attribute.service';
import { ProductAttribute } from 'app/shared/model/product-attribute.model';

describe('Component Tests', () => {
  describe('ProductAttribute Management Update Component', () => {
    let comp: ProductAttributeUpdateComponent;
    let fixture: ComponentFixture<ProductAttributeUpdateComponent>;
    let service: ProductAttributeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductAttributeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductAttributeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductAttributeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAttributeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductAttribute(123);
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
        const entity = new ProductAttribute();
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
