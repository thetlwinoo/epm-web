import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IReviewLines } from 'app/shared/model/review-lines.model';

@Component({
  selector: 'jhi-review-lines-detail',
  templateUrl: './review-lines-detail.component.html'
})
export class ReviewLinesDetailComponent implements OnInit {
  reviewLines: IReviewLines;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reviewLines }) => {
      this.reviewLines = reviewLines;
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
