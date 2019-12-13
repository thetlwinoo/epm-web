import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReviewLines } from 'app/shared/model/review-lines.model';
import { ReviewLinesService } from './review-lines.service';

@Component({
  templateUrl: './review-lines-delete-dialog.component.html'
})
export class ReviewLinesDeleteDialogComponent {
  reviewLines: IReviewLines;

  constructor(
    protected reviewLinesService: ReviewLinesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.reviewLinesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'reviewLinesListModification',
        content: 'Deleted an reviewLines'
      });
      this.activeModal.dismiss(true);
    });
  }
}
