import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBuyingGroups } from 'app/shared/model/buying-groups.model';

type EntityResponseType = HttpResponse<IBuyingGroups>;
type EntityArrayResponseType = HttpResponse<IBuyingGroups[]>;

@Injectable({ providedIn: 'root' })
export class BuyingGroupsService {
  public resourceUrl = SERVER_API_URL + 'api/buying-groups';

  constructor(protected http: HttpClient) {}

  create(buyingGroups: IBuyingGroups): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(buyingGroups);
    return this.http
      .post<IBuyingGroups>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(buyingGroups: IBuyingGroups): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(buyingGroups);
    return this.http
      .put<IBuyingGroups>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBuyingGroups>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBuyingGroups[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(buyingGroups: IBuyingGroups): IBuyingGroups {
    const copy: IBuyingGroups = Object.assign({}, buyingGroups, {
      validFrom: buyingGroups.validFrom != null && buyingGroups.validFrom.isValid() ? buyingGroups.validFrom.toJSON() : null,
      validTo: buyingGroups.validTo != null && buyingGroups.validTo.isValid() ? buyingGroups.validTo.toJSON() : null
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
      res.body.forEach((buyingGroups: IBuyingGroups) => {
        buyingGroups.validFrom = buyingGroups.validFrom != null ? moment(buyingGroups.validFrom) : null;
        buyingGroups.validTo = buyingGroups.validTo != null ? moment(buyingGroups.validTo) : null;
      });
    }
    return res;
  }
}
