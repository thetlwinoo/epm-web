import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomerTransactions } from 'app/shared/model/customer-transactions.model';

type EntityResponseType = HttpResponse<ICustomerTransactions>;
type EntityArrayResponseType = HttpResponse<ICustomerTransactions[]>;

@Injectable({ providedIn: 'root' })
export class CustomerTransactionsService {
  public resourceUrl = SERVER_API_URL + 'api/customer-transactions';

  constructor(protected http: HttpClient) {}

  create(customerTransactions: ICustomerTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerTransactions);
    return this.http
      .post<ICustomerTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customerTransactions: ICustomerTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerTransactions);
    return this.http
      .put<ICustomerTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomerTransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomerTransactions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(customerTransactions: ICustomerTransactions): ICustomerTransactions {
    const copy: ICustomerTransactions = Object.assign({}, customerTransactions, {
      transactionDate:
        customerTransactions.transactionDate != null && customerTransactions.transactionDate.isValid()
          ? customerTransactions.transactionDate.toJSON()
          : null,
      finalizationDate:
        customerTransactions.finalizationDate != null && customerTransactions.finalizationDate.isValid()
          ? customerTransactions.finalizationDate.toJSON()
          : null,
      lastEditedWhen:
        customerTransactions.lastEditedWhen != null && customerTransactions.lastEditedWhen.isValid()
          ? customerTransactions.lastEditedWhen.toJSON()
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
      res.body.forEach((customerTransactions: ICustomerTransactions) => {
        customerTransactions.transactionDate =
          customerTransactions.transactionDate != null ? moment(customerTransactions.transactionDate) : null;
        customerTransactions.finalizationDate =
          customerTransactions.finalizationDate != null ? moment(customerTransactions.finalizationDate) : null;
        customerTransactions.lastEditedWhen =
          customerTransactions.lastEditedWhen != null ? moment(customerTransactions.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
