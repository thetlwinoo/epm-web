import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { PaymentTransactionsDeleteDialogComponent } from 'app/entities/payment-transactions/payment-transactions-delete-dialog.component';
import { PaymentTransactionsService } from 'app/entities/payment-transactions/payment-transactions.service';

describe('Component Tests', () => {
  describe('PaymentTransactions Management Delete Component', () => {
    let comp: PaymentTransactionsDeleteDialogComponent;
    let fixture: ComponentFixture<PaymentTransactionsDeleteDialogComponent>;
    let service: PaymentTransactionsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PaymentTransactionsDeleteDialogComponent]
      })
        .overrideTemplate(PaymentTransactionsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentTransactionsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentTransactionsService);
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
