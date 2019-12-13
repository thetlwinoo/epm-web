import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';

type EntityResponseType = HttpResponse<IDeliveryMethods>;
type EntityArrayResponseType = HttpResponse<IDeliveryMethods[]>;

@Injectable({ providedIn: 'root' })
export class DeliveryMethodsService {
  public resourceUrl = SERVER_API_URL + 'api/delivery-methods';

  constructor(protected http: HttpClient) {}

  create(deliveryMethods: IDeliveryMethods): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deliveryMethods);
    return this.http
      .post<IDeliveryMethods>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deliveryMethods: IDeliveryMethods): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deliveryMethods);
    return this.http
      .put<IDeliveryMethods>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeliveryMethods>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeliveryMethods[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(deliveryMethods: IDeliveryMethods): IDeliveryMethods {
    const copy: IDeliveryMethods = Object.assign({}, deliveryMethods, {
      validFrom: deliveryMethods.validFrom != null && deliveryMethods.validFrom.isValid() ? deliveryMethods.validFrom.toJSON() : null,
      validTo: deliveryMethods.validTo != null && deliveryMethods.validTo.isValid() ? deliveryMethods.validTo.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validFrom = res.body.validFrom != null ? moment(res.body.validFrom) : null;
      res.body.validTo = res.body.validTo != null ? moment(res.body.validTo) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((deliveryMethods: IDeliveryMethods) => {
        deliveryMethods.validFrom = deliveryMethods.validFrom != null ? moment(deliveryMethods.validFrom) : null;
        deliveryMethods.validTo = deliveryMethods.validTo != null ? moment(deliveryMethods.validTo) : null;
      });
    }
    return res;
  }
}
