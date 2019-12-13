import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { WishlistProductsDeleteDialogComponent } from 'app/entities/wishlist-products/wishlist-products-delete-dialog.component';
import { WishlistProductsService } from 'app/entities/wishlist-products/wishlist-products.service';

describe('Component Tests', () => {
  describe('WishlistProducts Management Delete Component', () => {
    let comp: WishlistProductsDeleteDialogComponent;
    let fixture: ComponentFixture<WishlistProductsDeleteDialogComponent>;
    let service: WishlistProductsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [WishlistProductsDeleteDialogComponent]
      })
        .overrideTemplate(WishlistProductsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WishlistProductsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WishlistProductsService);
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
