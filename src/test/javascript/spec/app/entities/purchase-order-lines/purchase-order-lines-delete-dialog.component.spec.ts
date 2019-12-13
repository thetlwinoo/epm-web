import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { PurchaseOrderLinesDeleteDialogComponent } from 'app/entities/purchase-order-lines/purchase-order-lines-delete-dialog.component';
import { PurchaseOrderLinesService } from 'app/entities/purchase-order-lines/purchase-order-lines.service';

describe('Component Tests', () => {
  describe('PurchaseOrderLines Management Delete Component', () => {
    let comp: PurchaseOrderLinesDeleteDialogComponent;
    let fixture: ComponentFixture<PurchaseOrderLinesDeleteDialogComponent>;
    let service: PurchaseOrderLinesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PurchaseOrderLinesDeleteDialogComponent]
      })
        .overrideTemplate(PurchaseOrderLinesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PurchaseOrderLinesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PurchaseOrderLinesService);
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
