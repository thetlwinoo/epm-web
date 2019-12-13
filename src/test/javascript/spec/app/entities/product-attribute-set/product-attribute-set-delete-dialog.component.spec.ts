import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ProductAttributeSetDeleteDialogComponent } from 'app/entities/product-attribute-set/product-attribute-set-delete-dialog.component';
import { ProductAttributeSetService } from 'app/entities/product-attribute-set/product-attribute-set.service';

describe('Component Tests', () => {
  describe('ProductAttributeSet Management Delete Component', () => {
    let comp: ProductAttributeSetDeleteDialogComponent;
    let fixture: ComponentFixture<ProductAttributeSetDeleteDialogComponent>;
    let service: ProductAttributeSetService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductAttributeSetDeleteDialogComponent]
      })
        .overrideTemplate(ProductAttributeSetDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductAttributeSetDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAttributeSetService);
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
