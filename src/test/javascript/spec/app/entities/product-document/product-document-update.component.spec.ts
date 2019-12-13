import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductDocumentUpdateComponent } from 'app/entities/product-document/product-document-update.component';
import { ProductDocumentService } from 'app/entities/product-document/product-document.service';
import { ProductDocument } from 'app/shared/model/product-document.model';

describe('Component Tests', () => {
  describe('ProductDocument Management Update Component', () => {
    let comp: ProductDocumentUpdateComponent;
    let fixture: ComponentFixture<ProductDocumentUpdateComponent>;
    let service: ProductDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductDocumentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductDocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductDocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductDocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductDocument(123);
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
        const entity = new ProductDocument();
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
