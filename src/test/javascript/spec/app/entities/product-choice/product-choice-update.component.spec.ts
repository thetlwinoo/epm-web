import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductChoiceUpdateComponent } from 'app/entities/product-choice/product-choice-update.component';
import { ProductChoiceService } from 'app/entities/product-choice/product-choice.service';
import { ProductChoice } from 'app/shared/model/product-choice.model';

describe('Component Tests', () => {
  describe('ProductChoice Management Update Component', () => {
    let comp: ProductChoiceUpdateComponent;
    let fixture: ComponentFixture<ProductChoiceUpdateComponent>;
    let service: ProductChoiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductChoiceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductChoiceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductChoiceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductChoiceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductChoice(123);
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
        const entity = new ProductChoice();
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
