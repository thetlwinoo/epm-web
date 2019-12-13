import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CompareProductsUpdateComponent } from 'app/entities/compare-products/compare-products-update.component';
import { CompareProductsService } from 'app/entities/compare-products/compare-products.service';
import { CompareProducts } from 'app/shared/model/compare-products.model';

describe('Component Tests', () => {
  describe('CompareProducts Management Update Component', () => {
    let comp: CompareProductsUpdateComponent;
    let fixture: ComponentFixture<CompareProductsUpdateComponent>;
    let service: CompareProductsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CompareProductsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompareProductsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompareProductsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompareProductsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompareProducts(123);
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
        const entity = new CompareProducts();
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
