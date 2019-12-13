import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductOptionUpdateComponent } from 'app/entities/product-option/product-option-update.component';
import { ProductOptionService } from 'app/entities/product-option/product-option.service';
import { ProductOption } from 'app/shared/model/product-option.model';

describe('Component Tests', () => {
  describe('ProductOption Management Update Component', () => {
    let comp: ProductOptionUpdateComponent;
    let fixture: ComponentFixture<ProductOptionUpdateComponent>;
    let service: ProductOptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductOptionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductOptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductOptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductOptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductOption(123);
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
        const entity = new ProductOption();
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
