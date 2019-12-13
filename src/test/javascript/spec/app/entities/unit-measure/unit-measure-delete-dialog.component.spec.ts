import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { UnitMeasureDeleteDialogComponent } from 'app/entities/unit-measure/unit-measure-delete-dialog.component';
import { UnitMeasureService } from 'app/entities/unit-measure/unit-measure.service';

describe('Component Tests', () => {
  describe('UnitMeasure Management Delete Component', () => {
    let comp: UnitMeasureDeleteDialogComponent;
    let fixture: ComponentFixture<UnitMeasureDeleteDialogComponent>;
    let service: UnitMeasureService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [UnitMeasureDeleteDialogComponent]
      })
        .overrideTemplate(UnitMeasureDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UnitMeasureDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UnitMeasureService);
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
