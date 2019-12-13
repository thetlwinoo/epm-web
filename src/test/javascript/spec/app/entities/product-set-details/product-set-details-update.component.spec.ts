import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductSetDetailsUpdateComponent } from 'app/entities/product-set-details/product-set-details-update.component';
import { ProductSetDetailsService } from 'app/entities/product-set-details/product-set-details.service';
import { ProductSetDetails } from 'app/shared/model/product-set-details.model';

describe('Component Tests', () => {
  describe('ProductSetDetails Management Update Component', () => {
    let comp: ProductSetDetailsUpdateComponent;
    let fixture: ComponentFixture<ProductSetDetailsUpdateComponent>;
    let service: ProductSetDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductSetDetailsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductSetDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductSetDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductSetDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductSetDetails(123);
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
        const entity = new ProductSetDetails();
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
