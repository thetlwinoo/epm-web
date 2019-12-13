import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrders } from 'app/shared/model/orders.model';

type EntityResponseType = HttpResponse<IOrders>;
type EntityArrayResponseType = HttpResponse<IOrders[]>;

@Injectable({ providedIn: 'root' })
export class OrdersService {
  public resourceUrl = SERVER_API_URL + 'api/orders';

  constructor(protected http: HttpClient) {}

  create(orders: IOrders): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orders);
    return this.http
      .post<IOrders>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orders: IOrders): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orders);
    return this.http
      .put<IOrders>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrders>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrders[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orders: IOrders): IOrders {
    const copy: IOrders = Object.assign({}, orders, {
      orderDate: orders.orderDate != null && orders.orderDate.isValid() ? orders.orderDate.toJSON() : null,
      dueDate: orders.dueDate != null && orders.dueDate.isValid() ? orders.dueDate.toJSON() : null,
      shipDate: orders.shipDate != null && orders.shipDate.isValid() ? orders.shipDate.toJSON() : null,
      pickingCompletedWhen:
        orders.pickingCompletedWhen != null && orders.pickingCompletedWhen.isValid() ? orders.pickingCompletedWhen.toJSON() : null,
      lastEditedWhen: orders.lastEditedWhen != null && orders.lastEditedWhen.isValid() ? orders.lastEditedWhen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.orderDate = res.body.orderDate != null ? moment(res.body.orderDate) : null;
      res.body.dueDate = res.body.dueDate != null ? moment(res.body.dueDate) : null;
      res.body.shipDate = res.body.shipDate != null ? moment(res.body.shipDate) : null;
      res.body.pickingCompletedWhen = res.body.pickingCompletedWhen != null ? moment(res.body.pickingCompletedWhen) : null;
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orders: IOrders) => {
        orders.orderDate = orders.orderDate != null ? moment(orders.orderDate) : null;
        orders.dueDate = orders.dueDate != null ? moment(orders.dueDate) : null;
        orders.shipDate = orders.shipDate != null ? moment(orders.shipDate) : null;
        orders.pickingCompletedWhen = orders.pickingCompletedWhen != null ? moment(orders.pickingCompletedWhen) : null;
        orders.lastEditedWhen = orders.lastEditedWhen != null ? moment(orders.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
