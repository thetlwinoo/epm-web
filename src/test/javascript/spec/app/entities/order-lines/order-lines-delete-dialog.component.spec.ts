import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { OrderLinesDeleteDialogComponent } from 'app/entities/order-lines/order-lines-delete-dialog.component';
import { OrderLinesService } from 'app/entities/order-lines/order-lines.service';

describe('Component Tests', () => {
  describe('OrderLines Management Delete Component', () => {
    let comp: OrderLinesDeleteDialogComponent;
    let fixture: ComponentFixture<OrderLinesDeleteDialogComponent>;
    let service: OrderLinesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [OrderLinesDeleteDialogComponent]
      })
        .overrideTemplate(OrderLinesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderLinesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderLinesService);
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
