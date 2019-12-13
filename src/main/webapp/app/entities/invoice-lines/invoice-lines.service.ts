import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInvoiceLines } from 'app/shared/model/invoice-lines.model';

type EntityResponseType = HttpResponse<IInvoiceLines>;
type EntityArrayResponseType = HttpResponse<IInvoiceLines[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceLinesService {
  public resourceUrl = SERVER_API_URL + 'api/invoice-lines';

  constructor(protected http: HttpClient) {}

  create(invoiceLines: IInvoiceLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoiceLines);
    return this.http
      .post<IInvoiceLines>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invoiceLines: IInvoiceLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoiceLines);
    return this.http
      .put<IInvoiceLines>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInvoiceLines>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoiceLines[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(invoiceLines: IInvoiceLines): IInvoiceLines {
    const copy: IInvoiceLines = Object.assign({}, invoiceLines, {
      lastEditedWhen:
        invoiceLines.lastEditedWhen != null && invoiceLines.lastEditedWhen.isValid() ? invoiceLines.lastEditedWhen.toJSON() : null
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
      res.body.forEach((invoiceLines: IInvoiceLines) => {
        invoiceLines.lastEditedWhen = invoiceLines.lastEditedWhen != null ? moment(invoiceLines.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
