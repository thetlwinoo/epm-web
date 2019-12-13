import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ProductDocumentDeleteDialogComponent } from 'app/entities/product-document/product-document-delete-dialog.component';
import { ProductDocumentService } from 'app/entities/product-document/product-document.service';

describe('Component Tests', () => {
  describe('ProductDocument Management Delete Component', () => {
    let comp: ProductDocumentDeleteDialogComponent;
    let fixture: ComponentFixture<ProductDocumentDeleteDialogComponent>;
    let service: ProductDocumentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductDocumentDeleteDialogComponent]
      })
        .overrideTemplate(ProductDocumentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductDocumentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductDocumentService);
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
