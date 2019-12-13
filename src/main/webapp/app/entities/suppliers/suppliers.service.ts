import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISuppliers } from 'app/shared/model/suppliers.model';

type EntityResponseType = HttpResponse<ISuppliers>;
type EntityArrayResponseType = HttpResponse<ISuppliers[]>;

@Injectable({ providedIn: 'root' })
export class SuppliersService {
  public resourceUrl = SERVER_API_URL + 'api/suppliers';

  constructor(protected http: HttpClient) {}

  create(suppliers: ISuppliers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(suppliers);
    return this.http
      .post<ISuppliers>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(suppliers: ISuppliers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(suppliers);
    return this.http
      .put<ISuppliers>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISuppliers>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISuppliers[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(suppliers: ISuppliers): ISuppliers {
    const copy: ISuppliers = Object.assign({}, suppliers, {
      validFrom: suppliers.validFrom != null && suppliers.validFrom.isValid() ? suppliers.validFrom.toJSON() : null,
      validTo: suppliers.validTo != null && suppliers.validTo.isValid() ? suppliers.validTo.toJSON() : null
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
      res.body.forEach((suppliers: ISuppliers) => {
        suppliers.validFrom = suppliers.validFrom != null ? moment(suppliers.validFrom) : null;
        suppliers.validTo = suppliers.validTo != null ? moment(suppliers.validTo) : null;
      });
    }
    return res;
  }
}
