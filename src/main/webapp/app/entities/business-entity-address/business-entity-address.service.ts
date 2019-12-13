import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBusinessEntityAddress } from 'app/shared/model/business-entity-address.model';

type EntityResponseType = HttpResponse<IBusinessEntityAddress>;
type EntityArrayResponseType = HttpResponse<IBusinessEntityAddress[]>;

@Injectable({ providedIn: 'root' })
export class BusinessEntityAddressService {
  public resourceUrl = SERVER_API_URL + 'api/business-entity-addresses';

  constructor(protected http: HttpClient) {}

  create(businessEntityAddress: IBusinessEntityAddress): Observable<EntityResponseType> {
    return this.http.post<IBusinessEntityAddress>(this.resourceUrl, businessEntityAddress, { observe: 'response' });
  }

  update(businessEntityAddress: IBusinessEntityAddress): Observable<EntityResponseType> {
    return this.http.put<IBusinessEntityAddress>(this.resourceUrl, businessEntityAddress, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBusinessEntityAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBusinessEntityAddress[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
