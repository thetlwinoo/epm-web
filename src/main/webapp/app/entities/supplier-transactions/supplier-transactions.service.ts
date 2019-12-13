import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISupplierTransactions } from 'app/shared/model/supplier-transactions.model';

type EntityResponseType = HttpResponse<ISupplierTransactions>;
type EntityArrayResponseType = HttpResponse<ISupplierTransactions[]>;

@Injectable({ providedIn: 'root' })
export class SupplierTransactionsService {
  public resourceUrl = SERVER_API_URL + 'api/supplier-transactions';

  constructor(protected http: HttpClient) {}

  create(supplierTransactions: ISupplierTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supplierTransactions);
    return this.http
      .post<ISupplierTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(supplierTransactions: ISupplierTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supplierTransactions);
    return this.http
      .put<ISupplierTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISupplierTransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISupplierTransactions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(supplierTransactions: ISupplierTransactions): ISupplierTransactions {
    const copy: ISupplierTransactions = Object.assign({}, supplierTransactions, {
      transactionDate:
        supplierTransactions.transactionDate != null && supplierTransactions.transactionDate.isValid()
          ? supplierTransactions.transactionDate.toJSON()
          : null,
      finalizationDate:
        supplierTransactions.finalizationDate != null && supplierTransactions.finalizationDate.isValid()
          ? supplierTransactions.finalizationDate.toJSON()
          : null,
      lastEditedWhen:
        supplierTransactions.lastEditedWhen != null && supplierTransactions.lastEditedWhen.isValid()
          ? supplierTransactions.lastEditedWhen.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transactionDate = res.body.transactionDate != null ? moment(res.body.transactionDate) : null;
      res.body.finalizationDate = res.body.finalizationDate != null ? moment(res.body.finalizationDate) : null;
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((supplierTransactions: ISupplierTransactions) => {
        supplierTransactions.transactionDate =
          supplierTransactions.transactionDate != null ? moment(supplierTransactions.transactionDate) : null;
        supplierTransactions.finalizationDate =
          supplierTransactions.finalizationDate != null ? moment(supplierTransactions.finalizationDate) : null;
        supplierTransactions.lastEditedWhen =
          supplierTransactions.lastEditedWhen != null ? moment(supplierTransactions.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
