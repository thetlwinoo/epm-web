import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { TransactionTypesDeleteDialogComponent } from 'app/entities/transaction-types/transaction-types-delete-dialog.component';
import { TransactionTypesService } from 'app/entities/transaction-types/transaction-types.service';

describe('Component Tests', () => {
  describe('TransactionTypes Management Delete Component', () => {
    let comp: TransactionTypesDeleteDialogComponent;
    let fixture: ComponentFixture<TransactionTypesDeleteDialogComponent>;
    let service: TransactionTypesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [TransactionTypesDeleteDialogComponent]
      })
        .overrideTemplate(TransactionTypesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionTypesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionTypesService);
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
