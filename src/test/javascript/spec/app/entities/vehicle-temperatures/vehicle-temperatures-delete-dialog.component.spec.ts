import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { VehicleTemperaturesDeleteDialogComponent } from 'app/entities/vehicle-temperatures/vehicle-temperatures-delete-dialog.component';
import { VehicleTemperaturesService } from 'app/entities/vehicle-temperatures/vehicle-temperatures.service';

describe('Component Tests', () => {
  describe('VehicleTemperatures Management Delete Component', () => {
    let comp: VehicleTemperaturesDeleteDialogComponent;
    let fixture: ComponentFixture<VehicleTemperaturesDeleteDialogComponent>;
    let service: VehicleTemperaturesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [VehicleTemperaturesDeleteDialogComponent]
      })
        .overrideTemplate(VehicleTemperaturesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleTemperaturesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleTemperaturesService);
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
