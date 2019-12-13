import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { MaterialsDeleteDialogComponent } from 'app/entities/materials/materials-delete-dialog.component';
import { MaterialsService } from 'app/entities/materials/materials.service';

describe('Component Tests', () => {
  describe('Materials Management Delete Component', () => {
    let comp: MaterialsDeleteDialogComponent;
    let fixture: ComponentFixture<MaterialsDeleteDialogComponent>;
    let service: MaterialsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [MaterialsDeleteDialogComponent]
      })
        .overrideTemplate(MaterialsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaterialsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaterialsService);
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
