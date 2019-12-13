import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';

type EntityResponseType = HttpResponse<IColdRoomTemperatures>;
type EntityArrayResponseType = HttpResponse<IColdRoomTemperatures[]>;

@Injectable({ providedIn: 'root' })
export class ColdRoomTemperaturesService {
  public resourceUrl = SERVER_API_URL + 'api/cold-room-temperatures';

  constructor(protected http: HttpClient) {}

  create(coldRoomTemperatures: IColdRoomTemperatures): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(coldRoomTemperatures);
    return this.http
      .post<IColdRoomTemperatures>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(coldRoomTemperatures: IColdRoomTemperatures): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(coldRoomTemperatures);
    return this.http
      .put<IColdRoomTemperatures>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IColdRoomTemperatures>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IColdRoomTemperatures[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(coldRoomTemperatures: IColdRoomTemperatures): IColdRoomTemperatures {
    const copy: IColdRoomTemperatures = Object.assign({}, coldRoomTemperatures, {
      recordedWhen:
        coldRoomTemperatures.recordedWhen != null && coldRoomTemperatures.recordedWhen.isValid()
          ? coldRoomTemperatures.recordedWhen.toJSON()
          : null,
      validFrom:
        coldRoomTemperatures.validFrom != null && coldRoomTemperatures.validFrom.isValid() ? coldRoomTemperatures.validFrom.toJSON() : null,
      validTo: coldRoomTemperatures.validTo != null && coldRoomTemperatures.validTo.isValid() ? coldRoomTemperatures.validTo.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.recordedWhen = res.body.recordedWhen != null ? moment(res.body.recordedWhen) : null;
      res.body.validFrom = res.body.validFrom != null ? moment(res.body.validFrom) : null;
      res.body.validTo = res.body.validTo != null ? moment(res.body.validTo) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((coldRoomTemperatures: IColdRoomTemperatures) => {
        coldRoomTemperatures.recordedWhen = coldRoomTemperatures.recordedWhen != null ? moment(coldRoomTemperatures.recordedWhen) : null;
        coldRoomTemperatures.validFrom = coldRoomTemperatures.validFrom != null ? moment(coldRoomTemperatures.validFrom) : null;
        coldRoomTemperatures.validTo = coldRoomTemperatures.validTo != null ? moment(coldRoomTemperatures.validTo) : null;
      });
    }
    return res;
  }
}
