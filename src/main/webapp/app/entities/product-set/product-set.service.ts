import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductSet } from 'app/shared/model/product-set.model';

type EntityResponseType = HttpResponse<IProductSet>;
type EntityArrayResponseType = HttpResponse<IProductSet[]>;

@Injectable({ providedIn: 'root' })
export class ProductSetService {
  public resourceUrl = SERVER_API_URL + 'api/product-sets';

  constructor(protected http: HttpClient) {}

  create(productSet: IProductSet): Observable<EntityResponseType> {
    return this.http.post<IProductSet>(this.resourceUrl, productSet, { observe: 'response' });
  }

  update(productSet: IProductSet): Observable<EntityResponseType> {
    return this.http.put<IProductSet>(this.resourceUrl, productSet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductSet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductSet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
