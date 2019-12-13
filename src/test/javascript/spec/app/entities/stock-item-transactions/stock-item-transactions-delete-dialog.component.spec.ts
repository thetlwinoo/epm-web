import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { StockItemTransactionsDeleteDialogComponent } from 'app/entities/stock-item-transactions/stock-item-transactions-delete-dialog.component';
import { StockItemTransactionsService } from 'app/entities/stock-item-transactions/stock-item-transactions.service';

describe('Component Tests', () => {
  describe('StockItemTransactions Management Delete Component', () => {
    let comp: StockItemTransactionsDeleteDialogComponent;
    let fixture: ComponentFixture<StockItemTransactionsDeleteDialogComponent>;
    let service: StockItemTransactionsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StockItemTransactionsDeleteDialogComponent]
      })
        .overrideTemplate(StockItemTransactionsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockItemTransactionsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockItemTransactionsService);
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
