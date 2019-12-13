import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompares } from 'app/shared/model/compares.model';

type EntityResponseType = HttpResponse<ICompares>;
type EntityArrayResponseType = HttpResponse<ICompares[]>;

@Injectable({ providedIn: 'root' })
export class ComparesService {
  public resourceUrl = SERVER_API_URL + 'api/compares';

  constructor(protected http: HttpClient) {}

  create(compares: ICompares): Observable<EntityResponseType> {
    return this.http.post<ICompares>(this.resourceUrl, compares, { observe: 'response' });
  }

  update(compares: ICompares): Observable<EntityResponseType> {
    return this.http.put<ICompares>(this.resourceUrl, compares, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompares>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompares[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
