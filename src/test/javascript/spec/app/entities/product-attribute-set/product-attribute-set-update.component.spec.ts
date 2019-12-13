import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductAttributeSetUpdateComponent } from 'app/entities/product-attribute-set/product-attribute-set-update.component';
import { ProductAttributeSetService } from 'app/entities/product-attribute-set/product-attribute-set.service';
import { ProductAttributeSet } from 'app/shared/model/product-attribute-set.model';

describe('Component Tests', () => {
  describe('ProductAttributeSet Management Update Component', () => {
    let comp: ProductAttributeSetUpdateComponent;
    let fixture: ComponentFixture<ProductAttributeSetUpdateComponent>;
    let service: ProductAttributeSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductAttributeSetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductAttributeSetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductAttributeSetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAttributeSetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductAttributeSet(123);
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
        const entity = new ProductAttributeSet();
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
