import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ColdRoomTemperaturesDeleteDialogComponent } from 'app/entities/cold-room-temperatures/cold-room-temperatures-delete-dialog.component';
import { ColdRoomTemperaturesService } from 'app/entities/cold-room-temperatures/cold-room-temperatures.service';

describe('Component Tests', () => {
  describe('ColdRoomTemperatures Management Delete Component', () => {
    let comp: ColdRoomTemperaturesDeleteDialogComponent;
    let fixture: ComponentFixture<ColdRoomTemperaturesDeleteDialogComponent>;
    let service: ColdRoomTemperaturesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ColdRoomTemperaturesDeleteDialogComponent]
      })
        .overrideTemplate(ColdRoomTemperaturesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ColdRoomTemperaturesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ColdRoomTemperaturesService);
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
