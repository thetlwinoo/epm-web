import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISystemParameters } from 'app/shared/model/system-parameters.model';

type EntityResponseType = HttpResponse<ISystemParameters>;
type EntityArrayResponseType = HttpResponse<ISystemParameters[]>;

@Injectable({ providedIn: 'root' })
export class SystemParametersService {
  public resourceUrl = SERVER_API_URL + 'api/system-parameters';

  constructor(protected http: HttpClient) {}

  create(systemParameters: ISystemParameters): Observable<EntityResponseType> {
    return this.http.post<ISystemParameters>(this.resourceUrl, systemParameters, { observe: 'response' });
  }

  update(systemParameters: ISystemParameters): Observable<EntityResponseType> {
    return this.http.put<ISystemParameters>(this.resourceUrl, systemParameters, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISystemParameters>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISystemParameters[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
