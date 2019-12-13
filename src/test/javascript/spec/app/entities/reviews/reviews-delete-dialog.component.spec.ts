import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { ReviewsDeleteDialogComponent } from 'app/entities/reviews/reviews-delete-dialog.component';
import { ReviewsService } from 'app/entities/reviews/reviews.service';

describe('Component Tests', () => {
  describe('Reviews Management Delete Component', () => {
    let comp: ReviewsDeleteDialogComponent;
    let fixture: ComponentFixture<ReviewsDeleteDialogComponent>;
    let service: ReviewsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ReviewsDeleteDialogComponent]
      })
        .overrideTemplate(ReviewsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReviewsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReviewsService);
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
