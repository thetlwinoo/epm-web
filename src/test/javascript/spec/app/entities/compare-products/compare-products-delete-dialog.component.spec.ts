import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { CompareProductsDeleteDialogComponent } from 'app/entities/compare-products/compare-products-delete-dialog.component';
import { CompareProductsService } from 'app/entities/compare-products/compare-products.service';

describe('Component Tests', () => {
  describe('CompareProducts Management Delete Component', () => {
    let comp: CompareProductsDeleteDialogComponent;
    let fixture: ComponentFixture<CompareProductsDeleteDialogComponent>;
    let service: CompareProductsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CompareProductsDeleteDialogComponent]
      })
        .overrideTemplate(CompareProductsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompareProductsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompareProductsService);
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
