import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUnitMeasure } from 'app/shared/model/unit-measure.model';

type EntityResponseType = HttpResponse<IUnitMeasure>;
type EntityArrayResponseType = HttpResponse<IUnitMeasure[]>;

@Injectable({ providedIn: 'root' })
export class UnitMeasureService {
  public resourceUrl = SERVER_API_URL + 'api/unit-measures';

  constructor(protected http: HttpClient) {}

  create(unitMeasure: IUnitMeasure): Observable<EntityResponseType> {
    return this.http.post<IUnitMeasure>(this.resourceUrl, unitMeasure, { observe: 'response' });
  }

  update(unitMeasure: IUnitMeasure): Observable<EntityResponseType> {
    return this.http.put<IUnitMeasure>(this.resourceUrl, unitMeasure, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUnitMeasure>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnitMeasure[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
