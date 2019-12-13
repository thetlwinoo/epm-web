import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { InvoiceLinesDeleteDialogComponent } from 'app/entities/invoice-lines/invoice-lines-delete-dialog.component';
import { InvoiceLinesService } from 'app/entities/invoice-lines/invoice-lines.service';

describe('Component Tests', () => {
  describe('InvoiceLines Management Delete Component', () => {
    let comp: InvoiceLinesDeleteDialogComponent;
    let fixture: ComponentFixture<InvoiceLinesDeleteDialogComponent>;
    let service: InvoiceLinesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [InvoiceLinesDeleteDialogComponent]
      })
        .overrideTemplate(InvoiceLinesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvoiceLinesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceLinesService);
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
