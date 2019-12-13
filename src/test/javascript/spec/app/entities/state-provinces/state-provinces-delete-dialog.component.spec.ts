import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { StateProvincesDeleteDialogComponent } from 'app/entities/state-provinces/state-provinces-delete-dialog.component';
import { StateProvincesService } from 'app/entities/state-provinces/state-provinces.service';

describe('Component Tests', () => {
  describe('StateProvinces Management Delete Component', () => {
    let comp: StateProvincesDeleteDialogComponent;
    let fixture: ComponentFixture<StateProvincesDeleteDialogComponent>;
    let service: StateProvincesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StateProvincesDeleteDialogComponent]
      })
        .overrideTemplate(StateProvincesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StateProvincesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StateProvincesService);
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
