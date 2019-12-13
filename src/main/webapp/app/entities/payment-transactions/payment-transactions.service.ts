import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';

type EntityResponseType = HttpResponse<IPaymentTransactions>;
type EntityArrayResponseType = HttpResponse<IPaymentTransactions[]>;

@Injectable({ providedIn: 'root' })
export class PaymentTransactionsService {
  public resourceUrl = SERVER_API_URL + 'api/payment-transactions';

  constructor(protected http: HttpClient) {}

  create(paymentTransactions: IPaymentTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentTransactions);
    return this.http
      .post<IPaymentTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paymentTransactions: IPaymentTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentTransactions);
    return this.http
      .put<IPaymentTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaymentTransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentTransactions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paymentTransactions: IPaymentTransactions): IPaymentTransactions {
    const copy: IPaymentTransactions = Object.assign({}, paymentTransactions, {
      lastEditedWhen:
        paymentTransactions.lastEditedWhen != null && paymentTransactions.lastEditedWhen.isValid()
          ? paymentTransactions.lastEditedWhen.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((paymentTransactions: IPaymentTransactions) => {
        paymentTransactions.lastEditedWhen = paymentTransactions.lastEditedWhen != null ? moment(paymentTransactions.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
