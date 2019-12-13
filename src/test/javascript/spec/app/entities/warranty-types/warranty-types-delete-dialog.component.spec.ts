import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { WarrantyTypesDeleteDialogComponent } from 'app/entities/warranty-types/warranty-types-delete-dialog.component';
import { WarrantyTypesService } from 'app/entities/warranty-types/warranty-types.service';

describe('Component Tests', () => {
  describe('WarrantyTypes Management Delete Component', () => {
    let comp: WarrantyTypesDeleteDialogComponent;
    let fixture: ComponentFixture<WarrantyTypesDeleteDialogComponent>;
    let service: WarrantyTypesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [WarrantyTypesDeleteDialogComponent]
      })
        .overrideTemplate(WarrantyTypesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WarrantyTypesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WarrantyTypesService);
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
