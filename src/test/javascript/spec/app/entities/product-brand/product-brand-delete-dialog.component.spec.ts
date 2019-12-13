import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ProductBrandDeleteDialogComponent } from 'app/entities/product-brand/product-brand-delete-dialog.component';
import { ProductBrandService } from 'app/entities/product-brand/product-brand.service';

describe('Component Tests', () => {
  describe('ProductBrand Management Delete Component', () => {
    let comp: ProductBrandDeleteDialogComponent;
    let fixture: ComponentFixture<ProductBrandDeleteDialogComponent>;
    let service: ProductBrandService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductBrandDeleteDialogComponent]
      })
        .overrideTemplate(ProductBrandDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductBrandDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductBrandService);
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
