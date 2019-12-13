import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { StockItemsDeleteDialogComponent } from 'app/entities/stock-items/stock-items-delete-dialog.component';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';

describe('Component Tests', () => {
  describe('StockItems Management Delete Component', () => {
    let comp: StockItemsDeleteDialogComponent;
    let fixture: ComponentFixture<StockItemsDeleteDialogComponent>;
    let service: StockItemsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StockItemsDeleteDialogComponent]
      })
        .overrideTemplate(StockItemsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockItemsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockItemsService);
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
