import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ProductSetDetailsDeleteDialogComponent } from 'app/entities/product-set-details/product-set-details-delete-dialog.component';
import { ProductSetDetailsService } from 'app/entities/product-set-details/product-set-details.service';

describe('Component Tests', () => {
  describe('ProductSetDetails Management Delete Component', () => {
    let comp: ProductSetDetailsDeleteDialogComponent;
    let fixture: ComponentFixture<ProductSetDetailsDeleteDialogComponent>;
    let service: ProductSetDetailsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductSetDetailsDeleteDialogComponent]
      })
        .overrideTemplate(ProductSetDetailsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductSetDetailsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductSetDetailsService);
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
