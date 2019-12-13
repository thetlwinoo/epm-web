import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from './reviews.service';

@Component({
  templateUrl: './reviews-delete-dialog.component.html'
})
export class ReviewsDeleteDialogComponent {
  reviews: IReviews;

  constructor(protected reviewsService: ReviewsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.reviewsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'reviewsListModification',
        content: 'Deleted an reviews'
      });
      this.activeModal.dismiss(true);
    });
  }
}
