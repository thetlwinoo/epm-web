import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPeople } from 'app/shared/model/people.model';

type EntityResponseType = HttpResponse<IPeople>;
type EntityArrayResponseType = HttpResponse<IPeople[]>;

@Injectable({ providedIn: 'root' })
export class PeopleService {
  public resourceUrl = SERVER_API_URL + 'api/people';

  constructor(protected http: HttpClient) {}

  create(people: IPeople): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(people);
    return this.http
      .post<IPeople>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(people: IPeople): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(people);
    return this.http
      .put<IPeople>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPeople>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPeople[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(people: IPeople): IPeople {
    const copy: IPeople = Object.assign({}, people, {
      validFrom: people.validFrom != null && people.validFrom.isValid() ? people.validFrom.toJSON() : null,
      validTo: people.validTo != null && people.validTo.isValid() ? people.validTo.toJSON() : null
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
      res.body.forEach((people: IPeople) => {
        people.validFrom = people.validFrom != null ? moment(people.validFrom) : null;
        people.validTo = people.validTo != null ? moment(people.validTo) : null;
      });
    }
    return res;
  }
}
