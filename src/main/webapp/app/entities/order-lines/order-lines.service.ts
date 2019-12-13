import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderLines } from 'app/shared/model/order-lines.model';

type EntityResponseType = HttpResponse<IOrderLines>;
type EntityArrayResponseType = HttpResponse<IOrderLines[]>;

@Injectable({ providedIn: 'root' })
export class OrderLinesService {
  public resourceUrl = SERVER_API_URL + 'api/order-lines';

  constructor(protected http: HttpClient) {}

  create(orderLines: IOrderLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderLines);
    return this.http
      .post<IOrderLines>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderLines: IOrderLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderLines);
    return this.http
      .put<IOrderLines>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderLines>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderLines[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orderLines: IOrderLines): IOrderLines {
    const copy: IOrderLines = Object.assign({}, orderLines, {
      pickingCompletedWhen:
        orderLines.pickingCompletedWhen != null && orderLines.pickingCompletedWhen.isValid()
          ? orderLines.pickingCompletedWhen.toJSON()
          : null,
      lastEditedWhen: orderLines.lastEditedWhen != null && orderLines.lastEditedWhen.isValid() ? orderLines.lastEditedWhen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.pickingCompletedWhen = res.body.pickingCompletedWhen != null ? moment(res.body.pickingCompletedWhen) : null;
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orderLines: IOrderLines) => {
        orderLines.pickingCompletedWhen = orderLines.pickingCompletedWhen != null ? moment(orderLines.pickingCompletedWhen) : null;
        orderLines.lastEditedWhen = orderLines.lastEditedWhen != null ? moment(orderLines.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
