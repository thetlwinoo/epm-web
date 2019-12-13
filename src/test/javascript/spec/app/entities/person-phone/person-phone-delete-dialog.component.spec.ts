import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { PersonPhoneDeleteDialogComponent } from 'app/entities/person-phone/person-phone-delete-dialog.component';
import { PersonPhoneService } from 'app/entities/person-phone/person-phone.service';

describe('Component Tests', () => {
  describe('PersonPhone Management Delete Component', () => {
    let comp: PersonPhoneDeleteDialogComponent;
    let fixture: ComponentFixture<PersonPhoneDeleteDialogComponent>;
    let service: PersonPhoneService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PersonPhoneDeleteDialogComponent]
      })
        .overrideTemplate(PersonPhoneDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonPhoneDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonPhoneService);
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
