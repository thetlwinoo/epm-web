import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReviewLines } from 'app/shared/model/review-lines.model';
import { ReviewLinesService } from './review-lines.service';
import { ReviewLinesDeleteDialogComponent } from './review-lines-delete-dialog.component';

@Component({
  selector: 'jhi-review-lines',
  templateUrl: './review-lines.component.html'
})
export class ReviewLinesComponent implements OnInit, OnDestroy {
  reviewLines: IReviewLines[];
  eventSubscriber: Subscription;

  constructor(
    protected reviewLinesService: ReviewLinesService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.reviewLinesService.query().subscribe((res: HttpResponse<IReviewLines[]>) => {
      this.reviewLines = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInReviewLines();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReviewLines) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInReviewLines() {
    this.eventSubscriber = this.eventManager.subscribe('reviewLinesListModification', () => this.loadAll());
  }

  delete(reviewLines: IReviewLines) {
    const modalRef = this.modalService.open(ReviewLinesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reviewLines = reviewLines;
  }
}
