import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IReviews } from 'app/shared/model/reviews.model';

@Component({
  selector: 'jhi-reviews-detail',
  templateUrl: './reviews-detail.component.html'
})
export class ReviewsDetailComponent implements OnInit {
  reviews: IReviews;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reviews }) => {
      this.reviews = reviews;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
