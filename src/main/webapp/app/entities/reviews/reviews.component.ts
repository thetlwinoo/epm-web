import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from './reviews.service';
import { ReviewsDeleteDialogComponent } from './reviews-delete-dialog.component';

@Component({
  selector: 'jhi-reviews',
  templateUrl: './reviews.component.html'
})
export class ReviewsComponent implements OnInit, OnDestroy {
  reviews: IReviews[];
  eventSubscriber: Subscription;

  constructor(
    protected reviewsService: ReviewsService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.reviewsService.query().subscribe((res: HttpResponse<IReviews[]>) => {
      this.reviews = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInReviews();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReviews) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInReviews() {
    this.eventSubscriber = this.eventManager.subscribe('reviewsListModification', () => this.loadAll());
  }

  delete(reviews: IReviews) {
    const modalRef = this.modalService.open(ReviewsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reviews = reviews;
  }
}
