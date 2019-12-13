import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReviews } from 'app/shared/model/reviews.model';

type EntityResponseType = HttpResponse<IReviews>;
type EntityArrayResponseType = HttpResponse<IReviews[]>;

@Injectable({ providedIn: 'root' })
export class ReviewsService {
  public resourceUrl = SERVER_API_URL + 'api/reviews';

  constructor(protected http: HttpClient) {}

  create(reviews: IReviews): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reviews);
    return this.http
      .post<IReviews>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reviews: IReviews): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reviews);
    return this.http
      .put<IReviews>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReviews>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReviews[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reviews: IReviews): IReviews {
    const copy: IReviews = Object.assign({}, reviews, {
      reviewDate: reviews.reviewDate != null && reviews.reviewDate.isValid() ? reviews.reviewDate.toJSON() : null,
      lastEditedWhen: reviews.lastEditedWhen != null && reviews.lastEditedWhen.isValid() ? reviews.lastEditedWhen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.reviewDate = res.body.reviewDate != null ? moment(res.body.reviewDate) : null;
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reviews: IReviews) => {
        reviews.reviewDate = reviews.reviewDate != null ? moment(reviews.reviewDate) : null;
        reviews.lastEditedWhen = reviews.lastEditedWhen != null ? moment(reviews.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
