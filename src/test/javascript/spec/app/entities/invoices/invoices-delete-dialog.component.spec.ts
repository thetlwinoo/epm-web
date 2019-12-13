import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { InvoicesDeleteDialogComponent } from 'app/entities/invoices/invoices-delete-dialog.component';
import { InvoicesService } from 'app/entities/invoices/invoices.service';

describe('Component Tests', () => {
  describe('Invoices Management Delete Component', () => {
    let comp: InvoicesDeleteDialogComponent;
    let fixture: ComponentFixture<InvoicesDeleteDialogComponent>;
    let service: InvoicesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [InvoicesDeleteDialogComponent]
      })
        .overrideTemplate(InvoicesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvoicesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoicesService);
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
