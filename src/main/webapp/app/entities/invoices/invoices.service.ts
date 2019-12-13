import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInvoices } from 'app/shared/model/invoices.model';

type EntityResponseType = HttpResponse<IInvoices>;
type EntityArrayResponseType = HttpResponse<IInvoices[]>;

@Injectable({ providedIn: 'root' })
export class InvoicesService {
  public resourceUrl = SERVER_API_URL + 'api/invoices';

  constructor(protected http: HttpClient) {}

  create(invoices: IInvoices): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoices);
    return this.http
      .post<IInvoices>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invoices: IInvoices): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoices);
    return this.http
      .put<IInvoices>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInvoices>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoices[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(invoices: IInvoices): IInvoices {
    const copy: IInvoices = Object.assign({}, invoices, {
      invoiceDate: invoices.invoiceDate != null && invoices.invoiceDate.isValid() ? invoices.invoiceDate.toJSON() : null,
      confirmedDeliveryTime:
        invoices.confirmedDeliveryTime != null && invoices.confirmedDeliveryTime.isValid() ? invoices.confirmedDeliveryTime.toJSON() : null,
      lastEditedWhen: invoices.lastEditedWhen != null && invoices.lastEditedWhen.isValid() ? invoices.lastEditedWhen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.invoiceDate = res.body.invoiceDate != null ? moment(res.body.invoiceDate) : null;
      res.body.confirmedDeliveryTime = res.body.confirmedDeliveryTime != null ? moment(res.body.confirmedDeliveryTime) : null;
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((invoices: IInvoices) => {
        invoices.invoiceDate = invoices.invoiceDate != null ? moment(invoices.invoiceDate) : null;
        invoices.confirmedDeliveryTime = invoices.confirmedDeliveryTime != null ? moment(invoices.confirmedDeliveryTime) : null;
        invoices.lastEditedWhen = invoices.lastEditedWhen != null ? moment(invoices.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
