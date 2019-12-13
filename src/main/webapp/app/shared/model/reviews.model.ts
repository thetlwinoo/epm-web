import { Moment } from 'moment';
import { IReviewLines } from 'app/shared/model/review-lines.model';

export interface IReviews {
  id?: number;
  name?: string;
  emailAddress?: string;
  reviewDate?: Moment;
  overAllSellerRating?: number;
  overAllSellerReview?: any;
  overAllDeliveryRating?: number;
  overAllDeliveryReview?: any;
  reviewAsAnonymous?: boolean;
  completedReview?: boolean;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  reviewLineLists?: IReviewLines[];
  orderId?: number;
}

export class Reviews implements IReviews {
  constructor(
    public id?: number,
    public name?: string,
    public emailAddress?: string,
    public reviewDate?: Moment,
    public overAllSellerRating?: number,
    public overAllSellerReview?: any,
    public overAllDeliveryRating?: number,
    public overAllDeliveryReview?: any,
    public reviewAsAnonymous?: boolean,
    public completedReview?: boolean,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public reviewLineLists?: IReviewLines[],
    public orderId?: number
  ) {
    this.reviewAsAnonymous = this.reviewAsAnonymous || false;
    this.completedReview = this.completedReview || false;
  }
}
