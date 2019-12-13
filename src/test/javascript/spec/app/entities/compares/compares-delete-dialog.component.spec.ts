import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ComparesDeleteDialogComponent } from 'app/entities/compares/compares-delete-dialog.component';
import { ComparesService } from 'app/entities/compares/compares.service';

describe('Component Tests', () => {
  describe('Compares Management Delete Component', () => {
    let comp: ComparesDeleteDialogComponent;
    let fixture: ComponentFixture<ComparesDeleteDialogComponent>;
    let service: ComparesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ComparesDeleteDialogComponent]
      })
        .overrideTemplate(ComparesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComparesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComparesService);
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
