import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductSetUpdateComponent } from 'app/entities/product-set/product-set-update.component';
import { ProductSetService } from 'app/entities/product-set/product-set.service';
import { ProductSet } from 'app/shared/model/product-set.model';

describe('Component Tests', () => {
  describe('ProductSet Management Update Component', () => {
    let comp: ProductSetUpdateComponent;
    let fixture: ComponentFixture<ProductSetUpdateComponent>;
    let service: ProductSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductSetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductSetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductSetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductSetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductSet(123);
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
        const entity = new ProductSet();
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
