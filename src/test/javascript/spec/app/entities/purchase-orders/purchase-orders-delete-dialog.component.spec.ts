import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { PurchaseOrdersDeleteDialogComponent } from 'app/entities/purchase-orders/purchase-orders-delete-dialog.component';
import { PurchaseOrdersService } from 'app/entities/purchase-orders/purchase-orders.service';

describe('Component Tests', () => {
  describe('PurchaseOrders Management Delete Component', () => {
    let comp: PurchaseOrdersDeleteDialogComponent;
    let fixture: ComponentFixture<PurchaseOrdersDeleteDialogComponent>;
    let service: PurchaseOrdersService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PurchaseOrdersDeleteDialogComponent]
      })
        .overrideTemplate(PurchaseOrdersDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PurchaseOrdersDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PurchaseOrdersService);
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
