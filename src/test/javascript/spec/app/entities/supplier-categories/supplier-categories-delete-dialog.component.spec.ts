import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { SupplierCategoriesDeleteDialogComponent } from 'app/entities/supplier-categories/supplier-categories-delete-dialog.component';
import { SupplierCategoriesService } from 'app/entities/supplier-categories/supplier-categories.service';

describe('Component Tests', () => {
  describe('SupplierCategories Management Delete Component', () => {
    let comp: SupplierCategoriesDeleteDialogComponent;
    let fixture: ComponentFixture<SupplierCategoriesDeleteDialogComponent>;
    let service: SupplierCategoriesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SupplierCategoriesDeleteDialogComponent]
      })
        .overrideTemplate(SupplierCategoriesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierCategoriesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierCategoriesService);
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
