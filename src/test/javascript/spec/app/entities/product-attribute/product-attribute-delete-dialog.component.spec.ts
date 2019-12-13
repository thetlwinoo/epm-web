import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ProductAttributeDeleteDialogComponent } from 'app/entities/product-attribute/product-attribute-delete-dialog.component';
import { ProductAttributeService } from 'app/entities/product-attribute/product-attribute.service';

describe('Component Tests', () => {
  describe('ProductAttribute Management Delete Component', () => {
    let comp: ProductAttributeDeleteDialogComponent;
    let fixture: ComponentFixture<ProductAttributeDeleteDialogComponent>;
    let service: ProductAttributeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductAttributeDeleteDialogComponent]
      })
        .overrideTemplate(ProductAttributeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductAttributeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAttributeService);
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
