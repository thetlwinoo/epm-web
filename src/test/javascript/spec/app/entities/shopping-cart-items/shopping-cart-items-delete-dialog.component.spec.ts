import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ShoppingCartItemsDeleteDialogComponent } from 'app/entities/shopping-cart-items/shopping-cart-items-delete-dialog.component';
import { ShoppingCartItemsService } from 'app/entities/shopping-cart-items/shopping-cart-items.service';

describe('Component Tests', () => {
  describe('ShoppingCartItems Management Delete Component', () => {
    let comp: ShoppingCartItemsDeleteDialogComponent;
    let fixture: ComponentFixture<ShoppingCartItemsDeleteDialogComponent>;
    let service: ShoppingCartItemsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ShoppingCartItemsDeleteDialogComponent]
      })
        .overrideTemplate(ShoppingCartItemsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShoppingCartItemsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShoppingCartItemsService);
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
