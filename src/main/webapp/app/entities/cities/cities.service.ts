import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICities } from 'app/shared/model/cities.model';

type EntityResponseType = HttpResponse<ICities>;
type EntityArrayResponseType = HttpResponse<ICities[]>;

@Injectable({ providedIn: 'root' })
export class CitiesService {
  public resourceUrl = SERVER_API_URL + 'api/cities';

  constructor(protected http: HttpClient) {}

  create(cities: ICities): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cities);
    return this.http
      .post<ICities>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cities: ICities): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cities);
    return this.http
      .put<ICities>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICities>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICities[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cities: ICities): ICities {
    const copy: ICities = Object.assign({}, cities, {
      validFrom: cities.validFrom != null && cities.validFrom.isValid() ? cities.validFrom.toJSON() : null,
      validTo: cities.validTo != null && cities.validTo.isValid() ? cities.validTo.toJSON() : null
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
      res.body.forEach((cities: ICities) => {
        cities.validFrom = cities.validFrom != null ? moment(cities.validFrom) : null;
        cities.validTo = cities.validTo != null ? moment(cities.validTo) : null;
      });
    }
    return res;
  }
}
