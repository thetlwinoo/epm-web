import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ShipMethodDeleteDialogComponent } from 'app/entities/ship-method/ship-method-delete-dialog.component';
import { ShipMethodService } from 'app/entities/ship-method/ship-method.service';

describe('Component Tests', () => {
  describe('ShipMethod Management Delete Component', () => {
    let comp: ShipMethodDeleteDialogComponent;
    let fixture: ComponentFixture<ShipMethodDeleteDialogComponent>;
    let service: ShipMethodService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ShipMethodDeleteDialogComponent]
      })
        .overrideTemplate(ShipMethodDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShipMethodDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShipMethodService);
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
