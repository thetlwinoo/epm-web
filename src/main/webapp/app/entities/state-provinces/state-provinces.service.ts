import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStateProvinces } from 'app/shared/model/state-provinces.model';

type EntityResponseType = HttpResponse<IStateProvinces>;
type EntityArrayResponseType = HttpResponse<IStateProvinces[]>;

@Injectable({ providedIn: 'root' })
export class StateProvincesService {
  public resourceUrl = SERVER_API_URL + 'api/state-provinces';

  constructor(protected http: HttpClient) {}

  create(stateProvinces: IStateProvinces): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stateProvinces);
    return this.http
      .post<IStateProvinces>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stateProvinces: IStateProvinces): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stateProvinces);
    return this.http
      .put<IStateProvinces>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStateProvinces>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStateProvinces[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(stateProvinces: IStateProvinces): IStateProvinces {
    const copy: IStateProvinces = Object.assign({}, stateProvinces, {
      validFrom: stateProvinces.validFrom != null && stateProvinces.validFrom.isValid() ? stateProvinces.validFrom.toJSON() : null,
      validTo: stateProvinces.validTo != null && stateProvinces.validTo.isValid() ? stateProvinces.validTo.toJSON() : null
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
      res.body.forEach((stateProvinces: IStateProvinces) => {
        stateProvinces.validFrom = stateProvinces.validFrom != null ? moment(stateProvinces.validFrom) : null;
        stateProvinces.validTo = stateProvinces.validTo != null ? moment(stateProvinces.validTo) : null;
      });
    }
    return res;
  }
}
