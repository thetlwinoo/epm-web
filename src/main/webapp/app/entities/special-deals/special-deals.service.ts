import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpecialDeals } from 'app/shared/model/special-deals.model';

type EntityResponseType = HttpResponse<ISpecialDeals>;
type EntityArrayResponseType = HttpResponse<ISpecialDeals[]>;

@Injectable({ providedIn: 'root' })
export class SpecialDealsService {
  public resourceUrl = SERVER_API_URL + 'api/special-deals';

  constructor(protected http: HttpClient) {}

  create(specialDeals: ISpecialDeals): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(specialDeals);
    return this.http
      .post<ISpecialDeals>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(specialDeals: ISpecialDeals): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(specialDeals);
    return this.http
      .put<ISpecialDeals>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISpecialDeals>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISpecialDeals[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(specialDeals: ISpecialDeals): ISpecialDeals {
    const copy: ISpecialDeals = Object.assign({}, specialDeals, {
      startDate: specialDeals.startDate != null && specialDeals.startDate.isValid() ? specialDeals.startDate.toJSON() : null,
      endDate: specialDeals.endDate != null && specialDeals.endDate.isValid() ? specialDeals.endDate.toJSON() : null,
      lastEditedWhen:
        specialDeals.lastEditedWhen != null && specialDeals.lastEditedWhen.isValid() ? specialDeals.lastEditedWhen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((specialDeals: ISpecialDeals) => {
        specialDeals.startDate = specialDeals.startDate != null ? moment(specialDeals.startDate) : null;
        specialDeals.endDate = specialDeals.endDate != null ? moment(specialDeals.endDate) : null;
        specialDeals.lastEditedWhen = specialDeals.lastEditedWhen != null ? moment(specialDeals.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
