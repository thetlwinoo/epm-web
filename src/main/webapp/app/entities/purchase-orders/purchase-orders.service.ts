import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';

type EntityResponseType = HttpResponse<IPurchaseOrders>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrders[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrdersService {
  public resourceUrl = SERVER_API_URL + 'api/purchase-orders';

  constructor(protected http: HttpClient) {}

  create(purchaseOrders: IPurchaseOrders): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseOrders);
    return this.http
      .post<IPurchaseOrders>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(purchaseOrders: IPurchaseOrders): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseOrders);
    return this.http
      .put<IPurchaseOrders>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPurchaseOrders>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPurchaseOrders[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(purchaseOrders: IPurchaseOrders): IPurchaseOrders {
    const copy: IPurchaseOrders = Object.assign({}, purchaseOrders, {
      orderDate: purchaseOrders.orderDate != null && purchaseOrders.orderDate.isValid() ? purchaseOrders.orderDate.toJSON() : null,
      expectedDeliveryDate:
        purchaseOrders.expectedDeliveryDate != null && purchaseOrders.expectedDeliveryDate.isValid()
          ? purchaseOrders.expectedDeliveryDate.toJSON()
          : null,
      lastEditedWhen:
        purchaseOrders.lastEditedWhen != null && purchaseOrders.lastEditedWhen.isValid() ? purchaseOrders.lastEditedWhen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.orderDate = res.body.orderDate != null ? moment(res.body.orderDate) : null;
      res.body.expectedDeliveryDate = res.body.expectedDeliveryDate != null ? moment(res.body.expectedDeliveryDate) : null;
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((purchaseOrders: IPurchaseOrders) => {
        purchaseOrders.orderDate = purchaseOrders.orderDate != null ? moment(purchaseOrders.orderDate) : null;
        purchaseOrders.expectedDeliveryDate =
          purchaseOrders.expectedDeliveryDate != null ? moment(purchaseOrders.expectedDeliveryDate) : null;
        purchaseOrders.lastEditedWhen = purchaseOrders.lastEditedWhen != null ? moment(purchaseOrders.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
