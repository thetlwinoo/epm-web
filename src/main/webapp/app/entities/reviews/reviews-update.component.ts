import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IReviews, Reviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from './reviews.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders/orders.service';

@Component({
  selector: 'jhi-reviews-update',
  templateUrl: './reviews-update.component.html'
})
export class ReviewsUpdateComponent implements OnInit {
  isSaving: boolean;

  orders: IOrders[];

  editForm = this.fb.group({
    id: [],
    name: [],
    emailAddress: [null, [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    reviewDate: [],
    overAllSellerRating: [],
    overAllSellerReview: [],
    overAllDeliveryRating: [],
    overAllDeliveryReview: [],
    reviewAsAnonymous: [],
    completedReview: [],
    lastEditedBy: [],
    lastEditedWhen: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected reviewsService: ReviewsService,
    protected ordersService: OrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reviews }) => {
      this.updateForm(reviews);
    });
    this.ordersService
      .query()
      .subscribe((res: HttpResponse<IOrders[]>) => (this.orders = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(reviews: IReviews) {
    this.editForm.patchValue({
      id: reviews.id,
      name: reviews.name,
      emailAddress: reviews.emailAddress,
      reviewDate: reviews.reviewDate != null ? reviews.reviewDate.format(DATE_TIME_FORMAT) : null,
      overAllSellerRating: reviews.overAllSellerRating,
      overAllSellerReview: reviews.overAllSellerReview,
      overAllDeliveryRating: reviews.overAllDeliveryRating,
      overAllDeliveryReview: reviews.overAllDeliveryReview,
      reviewAsAnonymous: reviews.reviewAsAnonymous,
      completedReview: reviews.completedReview,
      lastEditedBy: reviews.lastEditedBy,
      lastEditedWhen: reviews.lastEditedWhen != null ? reviews.lastEditedWhen.format(DATE_TIME_FORMAT) : null
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const reviews = this.createFromForm();
    if (reviews.id !== undefined) {
      this.subscribeToSaveResponse(this.reviewsService.update(reviews));
    } else {
      this.subscribeToSaveResponse(this.reviewsService.create(reviews));
    }
  }

  private createFromForm(): IReviews {
    return {
      ...new Reviews(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      emailAddress: this.editForm.get(['emailAddress']).value,
      reviewDate:
        this.editForm.get(['reviewDate']).value != null ? moment(this.editForm.get(['reviewDate']).value, DATE_TIME_FORMAT) : undefined,
      overAllSellerRating: this.editForm.get(['overAllSellerRating']).value,
      overAllSellerReview: this.editForm.get(['overAllSellerReview']).value,
      overAllDeliveryRating: this.editForm.get(['overAllDeliveryRating']).value,
      overAllDeliveryReview: this.editForm.get(['overAllDeliveryReview']).value,
      reviewAsAnonymous: this.editForm.get(['reviewAsAnonymous']).value,
      completedReview: this.editForm.get(['completedReview']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReviews>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOrdersById(index: number, item: IOrders) {
    return item.id;
  }
}
