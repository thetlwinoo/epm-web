import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStockItemTemp } from 'app/shared/model/stock-item-temp.model';

type EntityResponseType = HttpResponse<IStockItemTemp>;
type EntityArrayResponseType = HttpResponse<IStockItemTemp[]>;

@Injectable({ providedIn: 'root' })
export class StockItemTempService {
  public resourceUrl = SERVER_API_URL + 'api/stock-item-temps';

  constructor(protected http: HttpClient) {}

  create(stockItemTemp: IStockItemTemp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockItemTemp);
    return this.http
      .post<IStockItemTemp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stockItemTemp: IStockItemTemp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockItemTemp);
    return this.http
      .put<IStockItemTemp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStockItemTemp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStockItemTemp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(stockItemTemp: IStockItemTemp): IStockItemTemp {
    const copy: IStockItemTemp = Object.assign({}, stockItemTemp, {
      sellStartDate:
        stockItemTemp.sellStartDate != null && stockItemTemp.sellStartDate.isValid() ? stockItemTemp.sellStartDate.toJSON() : null,
      sellEndDate: stockItemTemp.sellEndDate != null && stockItemTemp.sellEndDate.isValid() ? stockItemTemp.sellEndDate.toJSON() : null,
      lastEditedWhen:
        stockItemTemp.lastEditedWhen != null && stockItemTemp.lastEditedWhen.isValid() ? stockItemTemp.lastEditedWhen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sellStartDate = res.body.sellStartDate != null ? moment(res.body.sellStartDate) : null;
      res.body.sellEndDate = res.body.sellEndDate != null ? moment(res.body.sellEndDate) : null;
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((stockItemTemp: IStockItemTemp) => {
        stockItemTemp.sellStartDate = stockItemTemp.sellStartDate != null ? moment(stockItemTemp.sellStartDate) : null;
        stockItemTemp.sellEndDate = stockItemTemp.sellEndDate != null ? moment(stockItemTemp.sellEndDate) : null;
        stockItemTemp.lastEditedWhen = stockItemTemp.lastEditedWhen != null ? moment(stockItemTemp.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
