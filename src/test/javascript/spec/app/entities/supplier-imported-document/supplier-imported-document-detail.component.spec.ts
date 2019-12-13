import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { SupplierImportedDocumentDetailComponent } from 'app/entities/supplier-imported-document/supplier-imported-document-detail.component';
import { SupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';

describe('Component Tests', () => {
  describe('SupplierImportedDocument Management Detail Component', () => {
    let comp: SupplierImportedDocumentDetailComponent;
    let fixture: ComponentFixture<SupplierImportedDocumentDetailComponent>;
    const route = ({ data: of({ supplierImportedDocument: new SupplierImportedDocument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SupplierImportedDocumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SupplierImportedDocumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierImportedDocumentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supplierImportedDocument).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
