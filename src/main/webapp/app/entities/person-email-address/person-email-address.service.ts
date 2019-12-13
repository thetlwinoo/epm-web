import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonEmailAddress } from 'app/shared/model/person-email-address.model';

type EntityResponseType = HttpResponse<IPersonEmailAddress>;
type EntityArrayResponseType = HttpResponse<IPersonEmailAddress[]>;

@Injectable({ providedIn: 'root' })
export class PersonEmailAddressService {
  public resourceUrl = SERVER_API_URL + 'api/person-email-addresses';

  constructor(protected http: HttpClient) {}

  create(personEmailAddress: IPersonEmailAddress): Observable<EntityResponseType> {
    return this.http.post<IPersonEmailAddress>(this.resourceUrl, personEmailAddress, { observe: 'response' });
  }

  update(personEmailAddress: IPersonEmailAddress): Observable<EntityResponseType> {
    return this.http.put<IPersonEmailAddress>(this.resourceUrl, personEmailAddress, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonEmailAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonEmailAddress[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
