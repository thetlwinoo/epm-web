import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonPhone } from 'app/shared/model/person-phone.model';

type EntityResponseType = HttpResponse<IPersonPhone>;
type EntityArrayResponseType = HttpResponse<IPersonPhone[]>;

@Injectable({ providedIn: 'root' })
export class PersonPhoneService {
  public resourceUrl = SERVER_API_URL + 'api/person-phones';

  constructor(protected http: HttpClient) {}

  create(personPhone: IPersonPhone): Observable<EntityResponseType> {
    return this.http.post<IPersonPhone>(this.resourceUrl, personPhone, { observe: 'response' });
  }

  update(personPhone: IPersonPhone): Observable<EntityResponseType> {
    return this.http.put<IPersonPhone>(this.resourceUrl, personPhone, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonPhone>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonPhone[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
