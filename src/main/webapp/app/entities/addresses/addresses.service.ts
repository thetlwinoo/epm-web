import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAddresses } from 'app/shared/model/addresses.model';

type EntityResponseType = HttpResponse<IAddresses>;
type EntityArrayResponseType = HttpResponse<IAddresses[]>;

@Injectable({ providedIn: 'root' })
export class AddressesService {
  public resourceUrl = SERVER_API_URL + 'api/addresses';

  constructor(protected http: HttpClient) {}

  create(addresses: IAddresses): Observable<EntityResponseType> {
    return this.http.post<IAddresses>(this.resourceUrl, addresses, { observe: 'response' });
  }

  update(addresses: IAddresses): Observable<EntityResponseType> {
    return this.http.put<IAddresses>(this.resourceUrl, addresses, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAddresses>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAddresses[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
