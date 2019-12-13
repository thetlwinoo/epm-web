import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ProductChoiceDeleteDialogComponent } from 'app/entities/product-choice/product-choice-delete-dialog.component';
import { ProductChoiceService } from 'app/entities/product-choice/product-choice.service';

describe('Component Tests', () => {
  describe('ProductChoice Management Delete Component', () => {
    let comp: ProductChoiceDeleteDialogComponent;
    let fixture: ComponentFixture<ProductChoiceDeleteDialogComponent>;
    let service: ProductChoiceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductChoiceDeleteDialogComponent]
      })
        .overrideTemplate(ProductChoiceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductChoiceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductChoiceService);
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
