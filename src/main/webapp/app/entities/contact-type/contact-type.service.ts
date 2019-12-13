import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContactType } from 'app/shared/model/contact-type.model';

type EntityResponseType = HttpResponse<IContactType>;
type EntityArrayResponseType = HttpResponse<IContactType[]>;

@Injectable({ providedIn: 'root' })
export class ContactTypeService {
  public resourceUrl = SERVER_API_URL + 'api/contact-types';

  constructor(protected http: HttpClient) {}

  create(contactType: IContactType): Observable<EntityResponseType> {
    return this.http.post<IContactType>(this.resourceUrl, contactType, { observe: 'response' });
  }

  update(contactType: IContactType): Observable<EntityResponseType> {
    return this.http.put<IContactType>(this.resourceUrl, contactType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContactType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContactType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
