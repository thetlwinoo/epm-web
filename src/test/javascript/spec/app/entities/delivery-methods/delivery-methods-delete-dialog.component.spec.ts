import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { DeliveryMethodsDeleteDialogComponent } from 'app/entities/delivery-methods/delivery-methods-delete-dialog.component';
import { DeliveryMethodsService } from 'app/entities/delivery-methods/delivery-methods.service';

describe('Component Tests', () => {
  describe('DeliveryMethods Management Delete Component', () => {
    let comp: DeliveryMethodsDeleteDialogComponent;
    let fixture: ComponentFixture<DeliveryMethodsDeleteDialogComponent>;
    let service: DeliveryMethodsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [DeliveryMethodsDeleteDialogComponent]
      })
        .overrideTemplate(DeliveryMethodsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeliveryMethodsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeliveryMethodsService);
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
