import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { SupplierImportedDocumentDeleteDialogComponent } from 'app/entities/supplier-imported-document/supplier-imported-document-delete-dialog.component';
import { SupplierImportedDocumentService } from 'app/entities/supplier-imported-document/supplier-imported-document.service';

describe('Component Tests', () => {
  describe('SupplierImportedDocument Management Delete Component', () => {
    let comp: SupplierImportedDocumentDeleteDialogComponent;
    let fixture: ComponentFixture<SupplierImportedDocumentDeleteDialogComponent>;
    let service: SupplierImportedDocumentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SupplierImportedDocumentDeleteDialogComponent]
      })
        .overrideTemplate(SupplierImportedDocumentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierImportedDocumentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierImportedDocumentService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
