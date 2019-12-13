import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomerCategories } from 'app/shared/model/customer-categories.model';

type EntityResponseType = HttpResponse<ICustomerCategories>;
type EntityArrayResponseType = HttpResponse<ICustomerCategories[]>;

@Injectable({ providedIn: 'root' })
export class CustomerCategoriesService {
  public resourceUrl = SERVER_API_URL + 'api/customer-categories';

  constructor(protected http: HttpClient) {}

  create(customerCategories: ICustomerCategories): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerCategories);
    return this.http
      .post<ICustomerCategories>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customerCategories: ICustomerCategories): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerCategories);
    return this.http
      .put<ICustomerCategories>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomerCategories>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomerCategories[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(customerCategories: ICustomerCategories): ICustomerCategories {
    const copy: ICustomerCategories = Object.assign({}, customerCategories, {
      validFrom:
        customerCategories.validFrom != null && customerCategories.validFrom.isValid() ? customerCategories.validFrom.toJSON() : null,
      validTo: customerCategories.validTo != null && customerCategories.validTo.isValid() ? customerCategories.validTo.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validFrom = res.body.validFrom != null ? moment(res.body.validFrom) : null;
      res.body.validTo = res.body.validTo != null ? moment(res.body.validTo) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((customerCategories: ICustomerCategories) => {
        customerCategories.validFrom = customerCategories.validFrom != null ? moment(customerCategories.validFrom) : null;
        customerCategories.validTo = customerCategories.validTo != null ? moment(customerCategories.validTo) : null;
      });
    }
    return res;
  }
}
