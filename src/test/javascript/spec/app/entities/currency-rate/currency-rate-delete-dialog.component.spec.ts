import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { CurrencyRateDeleteDialogComponent } from 'app/entities/currency-rate/currency-rate-delete-dialog.component';
import { CurrencyRateService } from 'app/entities/currency-rate/currency-rate.service';

describe('Component Tests', () => {
  describe('CurrencyRate Management Delete Component', () => {
    let comp: CurrencyRateDeleteDialogComponent;
    let fixture: ComponentFixture<CurrencyRateDeleteDialogComponent>;
    let service: CurrencyRateService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CurrencyRateDeleteDialogComponent]
      })
        .overrideTemplate(CurrencyRateDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CurrencyRateDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurrencyRateService);
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
