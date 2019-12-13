import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { CustomerTransactionsDeleteDialogComponent } from 'app/entities/customer-transactions/customer-transactions-delete-dialog.component';
import { CustomerTransactionsService } from 'app/entities/customer-transactions/customer-transactions.service';

describe('Component Tests', () => {
  describe('CustomerTransactions Management Delete Component', () => {
    let comp: CustomerTransactionsDeleteDialogComponent;
    let fixture: ComponentFixture<CustomerTransactionsDeleteDialogComponent>;
    let service: CustomerTransactionsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CustomerTransactionsDeleteDialogComponent]
      })
        .overrideTemplate(CustomerTransactionsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerTransactionsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerTransactionsService);
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
