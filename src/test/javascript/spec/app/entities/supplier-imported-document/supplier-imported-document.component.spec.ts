import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { SupplierImportedDocumentComponent } from 'app/entities/supplier-imported-document/supplier-imported-document.component';
import { SupplierImportedDocumentService } from 'app/entities/supplier-imported-document/supplier-imported-document.service';
import { SupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';

describe('Component Tests', () => {
  describe('SupplierImportedDocument Management Component', () => {
    let comp: SupplierImportedDocumentComponent;
    let fixture: ComponentFixture<SupplierImportedDocumentComponent>;
    let service: SupplierImportedDocumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SupplierImportedDocumentComponent],
        providers: []
      })
        .overrideTemplate(SupplierImportedDocumentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierImportedDocumentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierImportedDocumentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SupplierImportedDocument(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.supplierImportedDocuments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
