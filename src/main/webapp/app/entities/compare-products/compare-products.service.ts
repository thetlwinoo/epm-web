import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompareProducts } from 'app/shared/model/compare-products.model';

type EntityResponseType = HttpResponse<ICompareProducts>;
type EntityArrayResponseType = HttpResponse<ICompareProducts[]>;

@Injectable({ providedIn: 'root' })
export class CompareProductsService {
  public resourceUrl = SERVER_API_URL + 'api/compare-products';

  constructor(protected http: HttpClient) {}

  create(compareProducts: ICompareProducts): Observable<EntityResponseType> {
    return this.http.post<ICompareProducts>(this.resourceUrl, compareProducts, { observe: 'response' });
  }

  update(compareProducts: ICompareProducts): Observable<EntityResponseType> {
    return this.http.put<ICompareProducts>(this.resourceUrl, compareProducts, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompareProducts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompareProducts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
