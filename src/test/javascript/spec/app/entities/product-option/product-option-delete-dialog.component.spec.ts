import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ProductOptionDeleteDialogComponent } from 'app/entities/product-option/product-option-delete-dialog.component';
import { ProductOptionService } from 'app/entities/product-option/product-option.service';

describe('Component Tests', () => {
  describe('ProductOption Management Delete Component', () => {
    let comp: ProductOptionDeleteDialogComponent;
    let fixture: ComponentFixture<ProductOptionDeleteDialogComponent>;
    let service: ProductOptionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductOptionDeleteDialogComponent]
      })
        .overrideTemplate(ProductOptionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductOptionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductOptionService);
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
