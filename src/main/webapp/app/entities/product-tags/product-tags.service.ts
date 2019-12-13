import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductTags } from 'app/shared/model/product-tags.model';

type EntityResponseType = HttpResponse<IProductTags>;
type EntityArrayResponseType = HttpResponse<IProductTags[]>;

@Injectable({ providedIn: 'root' })
export class ProductTagsService {
  public resourceUrl = SERVER_API_URL + 'api/product-tags';

  constructor(protected http: HttpClient) {}

  create(productTags: IProductTags): Observable<EntityResponseType> {
    return this.http.post<IProductTags>(this.resourceUrl, productTags, { observe: 'response' });
  }

  update(productTags: IProductTags): Observable<EntityResponseType> {
    return this.http.put<IProductTags>(this.resourceUrl, productTags, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductTags>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductTags[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
