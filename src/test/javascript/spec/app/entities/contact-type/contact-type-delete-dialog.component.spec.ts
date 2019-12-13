import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ContactTypeDeleteDialogComponent } from 'app/entities/contact-type/contact-type-delete-dialog.component';
import { ContactTypeService } from 'app/entities/contact-type/contact-type.service';

describe('Component Tests', () => {
  describe('ContactType Management Delete Component', () => {
    let comp: ContactTypeDeleteDialogComponent;
    let fixture: ComponentFixture<ContactTypeDeleteDialogComponent>;
    let service: ContactTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ContactTypeDeleteDialogComponent]
      })
        .overrideTemplate(ContactTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactTypeService);
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
