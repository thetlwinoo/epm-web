import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';

type EntityResponseType = HttpResponse<ISupplierCategories>;
type EntityArrayResponseType = HttpResponse<ISupplierCategories[]>;

@Injectable({ providedIn: 'root' })
export class SupplierCategoriesService {
  public resourceUrl = SERVER_API_URL + 'api/supplier-categories';

  constructor(protected http: HttpClient) {}

  create(supplierCategories: ISupplierCategories): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supplierCategories);
    return this.http
      .post<ISupplierCategories>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(supplierCategories: ISupplierCategories): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supplierCategories);
    return this.http
      .put<ISupplierCategories>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISupplierCategories>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISupplierCategories[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(supplierCategories: ISupplierCategories): ISupplierCategories {
    const copy: ISupplierCategories = Object.assign({}, supplierCategories, {
      validFrom:
        supplierCategories.validFrom != null && supplierCategories.validFrom.isValid() ? supplierCategories.validFrom.toJSON() : null,
      validTo: supplierCategories.validTo != null && supplierCategories.validTo.isValid() ? supplierCategories.validTo.toJSON() : null
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
      res.body.forEach((supplierCategories: ISupplierCategories) => {
        supplierCategories.validFrom = supplierCategories.validFrom != null ? moment(supplierCategories.validFrom) : null;
        supplierCategories.validTo = supplierCategories.validTo != null ? moment(supplierCategories.validTo) : null;
      });
    }
    return res;
  }
}
