import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductTagsUpdateComponent } from 'app/entities/product-tags/product-tags-update.component';
import { ProductTagsService } from 'app/entities/product-tags/product-tags.service';
import { ProductTags } from 'app/shared/model/product-tags.model';

describe('Component Tests', () => {
  describe('ProductTags Management Update Component', () => {
    let comp: ProductTagsUpdateComponent;
    let fixture: ComponentFixture<ProductTagsUpdateComponent>;
    let service: ProductTagsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductTagsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductTagsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductTagsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductTagsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductTags(123);
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
        const entity = new ProductTags();
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
