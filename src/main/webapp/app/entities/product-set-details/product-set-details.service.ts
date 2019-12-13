import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductSetDetails } from 'app/shared/model/product-set-details.model';

type EntityResponseType = HttpResponse<IProductSetDetails>;
type EntityArrayResponseType = HttpResponse<IProductSetDetails[]>;

@Injectable({ providedIn: 'root' })
export class ProductSetDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/product-set-details';

  constructor(protected http: HttpClient) {}

  create(productSetDetails: IProductSetDetails): Observable<EntityResponseType> {
    return this.http.post<IProductSetDetails>(this.resourceUrl, productSetDetails, { observe: 'response' });
  }

  update(productSetDetails: IProductSetDetails): Observable<EntityResponseType> {
    return this.http.put<IProductSetDetails>(this.resourceUrl, productSetDetails, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductSetDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductSetDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
