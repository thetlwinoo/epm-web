import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICulture } from 'app/shared/model/culture.model';

type EntityResponseType = HttpResponse<ICulture>;
type EntityArrayResponseType = HttpResponse<ICulture[]>;

@Injectable({ providedIn: 'root' })
export class CultureService {
  public resourceUrl = SERVER_API_URL + 'api/cultures';

  constructor(protected http: HttpClient) {}

  create(culture: ICulture): Observable<EntityResponseType> {
    return this.http.post<ICulture>(this.resourceUrl, culture, { observe: 'response' });
  }

  update(culture: ICulture): Observable<EntityResponseType> {
    return this.http.put<ICulture>(this.resourceUrl, culture, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICulture>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICulture[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
