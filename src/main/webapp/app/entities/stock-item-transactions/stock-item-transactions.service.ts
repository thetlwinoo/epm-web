import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStockItemTransactions } from 'app/shared/model/stock-item-transactions.model';

type EntityResponseType = HttpResponse<IStockItemTransactions>;
type EntityArrayResponseType = HttpResponse<IStockItemTransactions[]>;

@Injectable({ providedIn: 'root' })
export class StockItemTransactionsService {
  public resourceUrl = SERVER_API_URL + 'api/stock-item-transactions';

  constructor(protected http: HttpClient) {}

  create(stockItemTransactions: IStockItemTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockItemTransactions);
    return this.http
      .post<IStockItemTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stockItemTransactions: IStockItemTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockItemTransactions);
    return this.http
      .put<IStockItemTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStockItemTransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStockItemTransactions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(stockItemTransactions: IStockItemTransactions): IStockItemTransactions {
    const copy: IStockItemTransactions = Object.assign({}, stockItemTransactions, {
      transactionOccuredWhen:
        stockItemTransactions.transactionOccuredWhen != null && stockItemTransactions.transactionOccuredWhen.isValid()
          ? stockItemTransactions.transactionOccuredWhen.toJSON()
          : null,
      lastEditedWhen:
        stockItemTransactions.lastEditedWhen != null && stockItemTransactions.lastEditedWhen.isValid()
          ? stockItemTransactions.lastEditedWhen.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transactionOccuredWhen = res.body.transactionOccuredWhen != null ? moment(res.body.transactionOccuredWhen) : null;
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((stockItemTransactions: IStockItemTransactions) => {
        stockItemTransactions.transactionOccuredWhen =
          stockItemTransactions.transactionOccuredWhen != null ? moment(stockItemTransactions.transactionOccuredWhen) : null;
        stockItemTransactions.lastEditedWhen =
          stockItemTransactions.lastEditedWhen != null ? moment(stockItemTransactions.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
