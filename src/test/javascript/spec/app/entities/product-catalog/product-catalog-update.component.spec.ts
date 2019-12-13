import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductCatalogUpdateComponent } from 'app/entities/product-catalog/product-catalog-update.component';
import { ProductCatalogService } from 'app/entities/product-catalog/product-catalog.service';
import { ProductCatalog } from 'app/shared/model/product-catalog.model';

describe('Component Tests', () => {
  describe('ProductCatalog Management Update Component', () => {
    let comp: ProductCatalogUpdateComponent;
    let fixture: ComponentFixture<ProductCatalogUpdateComponent>;
    let service: ProductCatalogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductCatalogUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductCatalogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductCatalogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductCatalogService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductCatalog(123);
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
        const entity = new ProductCatalog();
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
