import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { StockItemHoldingsDeleteDialogComponent } from 'app/entities/stock-item-holdings/stock-item-holdings-delete-dialog.component';
import { StockItemHoldingsService } from 'app/entities/stock-item-holdings/stock-item-holdings.service';

describe('Component Tests', () => {
  describe('StockItemHoldings Management Delete Component', () => {
    let comp: StockItemHoldingsDeleteDialogComponent;
    let fixture: ComponentFixture<StockItemHoldingsDeleteDialogComponent>;
    let service: StockItemHoldingsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StockItemHoldingsDeleteDialogComponent]
      })
        .overrideTemplate(StockItemHoldingsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockItemHoldingsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockItemHoldingsService);
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
