import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { SystemParametersDeleteDialogComponent } from 'app/entities/system-parameters/system-parameters-delete-dialog.component';
import { SystemParametersService } from 'app/entities/system-parameters/system-parameters.service';

describe('Component Tests', () => {
  describe('SystemParameters Management Delete Component', () => {
    let comp: SystemParametersDeleteDialogComponent;
    let fixture: ComponentFixture<SystemParametersDeleteDialogComponent>;
    let service: SystemParametersService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SystemParametersDeleteDialogComponent]
      })
        .overrideTemplate(SystemParametersDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SystemParametersDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemParametersService);
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
