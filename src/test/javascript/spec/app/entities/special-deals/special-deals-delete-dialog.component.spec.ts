import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { SpecialDealsDeleteDialogComponent } from 'app/entities/special-deals/special-deals-delete-dialog.component';
import { SpecialDealsService } from 'app/entities/special-deals/special-deals.service';

describe('Component Tests', () => {
  describe('SpecialDeals Management Delete Component', () => {
    let comp: SpecialDealsDeleteDialogComponent;
    let fixture: ComponentFixture<SpecialDealsDeleteDialogComponent>;
    let service: SpecialDealsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SpecialDealsDeleteDialogComponent]
      })
        .overrideTemplate(SpecialDealsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpecialDealsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpecialDealsService);
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
