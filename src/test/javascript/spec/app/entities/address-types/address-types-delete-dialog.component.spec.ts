import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { AddressTypesDeleteDialogComponent } from 'app/entities/address-types/address-types-delete-dialog.component';
import { AddressTypesService } from 'app/entities/address-types/address-types.service';

describe('Component Tests', () => {
  describe('AddressTypes Management Delete Component', () => {
    let comp: AddressTypesDeleteDialogComponent;
    let fixture: ComponentFixture<AddressTypesDeleteDialogComponent>;
    let service: AddressTypesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [AddressTypesDeleteDialogComponent]
      })
        .overrideTemplate(AddressTypesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AddressTypesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AddressTypesService);
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
