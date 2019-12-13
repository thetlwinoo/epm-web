import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { PersonEmailAddressDeleteDialogComponent } from 'app/entities/person-email-address/person-email-address-delete-dialog.component';
import { PersonEmailAddressService } from 'app/entities/person-email-address/person-email-address.service';

describe('Component Tests', () => {
  describe('PersonEmailAddress Management Delete Component', () => {
    let comp: PersonEmailAddressDeleteDialogComponent;
    let fixture: ComponentFixture<PersonEmailAddressDeleteDialogComponent>;
    let service: PersonEmailAddressService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PersonEmailAddressDeleteDialogComponent]
      })
        .overrideTemplate(PersonEmailAddressDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonEmailAddressDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonEmailAddressService);
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
