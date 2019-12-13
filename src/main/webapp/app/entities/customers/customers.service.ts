import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomers } from 'app/shared/model/customers.model';

type EntityResponseType = HttpResponse<ICustomers>;
type EntityArrayResponseType = HttpResponse<ICustomers[]>;

@Injectable({ providedIn: 'root' })
export class CustomersService {
  public resourceUrl = SERVER_API_URL + 'api/customers';

  constructor(protected http: HttpClient) {}

  create(customers: ICustomers): Observable<EntityResponseType> {
    return this.http.post<ICustomers>(this.resourceUrl, customers, { observe: 'response' });
  }

  update(customers: ICustomers): Observable<EntityResponseType> {
    return this.http.put<ICustomers>(this.resourceUrl, customers, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
