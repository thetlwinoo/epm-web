import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { SupplierImportedDocumentUpdateComponent } from 'app/entities/supplier-imported-document/supplier-imported-document-update.component';
import { SupplierImportedDocumentService } from 'app/entities/supplier-imported-document/supplier-imported-document.service';
import { SupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';

describe('Component Tests', () => {
  describe('SupplierImportedDocument Management Update Component', () => {
    let comp: SupplierImportedDocumentUpdateComponent;
    let fixture: ComponentFixture<SupplierImportedDocumentUpdateComponent>;
    let service: SupplierImportedDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SupplierImportedDocumentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SupplierImportedDocumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierImportedDocumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierImportedDocumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SupplierImportedDocument(123);
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
        const entity = new SupplierImportedDocument();
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
