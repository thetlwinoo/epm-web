import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { UploadActionTypesDeleteDialogComponent } from 'app/entities/upload-action-types/upload-action-types-delete-dialog.component';
import { UploadActionTypesService } from 'app/entities/upload-action-types/upload-action-types.service';

describe('Component Tests', () => {
  describe('UploadActionTypes Management Delete Component', () => {
    let comp: UploadActionTypesDeleteDialogComponent;
    let fixture: ComponentFixture<UploadActionTypesDeleteDialogComponent>;
    let service: UploadActionTypesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [UploadActionTypesDeleteDialogComponent]
      })
        .overrideTemplate(UploadActionTypesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UploadActionTypesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UploadActionTypesService);
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
