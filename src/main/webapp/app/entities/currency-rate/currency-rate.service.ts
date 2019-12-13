import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICurrencyRate } from 'app/shared/model/currency-rate.model';

type EntityResponseType = HttpResponse<ICurrencyRate>;
type EntityArrayResponseType = HttpResponse<ICurrencyRate[]>;

@Injectable({ providedIn: 'root' })
export class CurrencyRateService {
  public resourceUrl = SERVER_API_URL + 'api/currency-rates';

  constructor(protected http: HttpClient) {}

  create(currencyRate: ICurrencyRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(currencyRate);
    return this.http
      .post<ICurrencyRate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(currencyRate: ICurrencyRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(currencyRate);
    return this.http
      .put<ICurrencyRate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICurrencyRate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICurrencyRate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(currencyRate: ICurrencyRate): ICurrencyRate {
    const copy: ICurrencyRate = Object.assign({}, currencyRate, {
      currencyRateDate:
        currencyRate.currencyRateDate != null && currencyRate.currencyRateDate.isValid() ? currencyRate.currencyRateDate.toJSON() : null,
      lastEditedWhen:
        currencyRate.lastEditedWhen != null && currencyRate.lastEditedWhen.isValid() ? currencyRate.lastEditedWhen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.currencyRateDate = res.body.currencyRateDate != null ? moment(res.body.currencyRateDate) : null;
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((currencyRate: ICurrencyRate) => {
        currencyRate.currencyRateDate = currencyRate.currencyRateDate != null ? moment(currencyRate.currencyRateDate) : null;
        currencyRate.lastEditedWhen = currencyRate.lastEditedWhen != null ? moment(currencyRate.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
