import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { SupplierTransactionsDeleteDialogComponent } from 'app/entities/supplier-transactions/supplier-transactions-delete-dialog.component';
import { SupplierTransactionsService } from 'app/entities/supplier-transactions/supplier-transactions.service';

describe('Component Tests', () => {
  describe('SupplierTransactions Management Delete Component', () => {
    let comp: SupplierTransactionsDeleteDialogComponent;
    let fixture: ComponentFixture<SupplierTransactionsDeleteDialogComponent>;
    let service: SupplierTransactionsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SupplierTransactionsDeleteDialogComponent]
      })
        .overrideTemplate(SupplierTransactionsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierTransactionsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierTransactionsService);
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
