import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { PhoneNumberTypeDeleteDialogComponent } from 'app/entities/phone-number-type/phone-number-type-delete-dialog.component';
import { PhoneNumberTypeService } from 'app/entities/phone-number-type/phone-number-type.service';

describe('Component Tests', () => {
  describe('PhoneNumberType Management Delete Component', () => {
    let comp: PhoneNumberTypeDeleteDialogComponent;
    let fixture: ComponentFixture<PhoneNumberTypeDeleteDialogComponent>;
    let service: PhoneNumberTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PhoneNumberTypeDeleteDialogComponent]
      })
        .overrideTemplate(PhoneNumberTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhoneNumberTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhoneNumberTypeService);
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
