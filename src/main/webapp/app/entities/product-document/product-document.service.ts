import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductDocument } from 'app/shared/model/product-document.model';

type EntityResponseType = HttpResponse<IProductDocument>;
type EntityArrayResponseType = HttpResponse<IProductDocument[]>;

@Injectable({ providedIn: 'root' })
export class ProductDocumentService {
  public resourceUrl = SERVER_API_URL + 'api/product-documents';

  constructor(protected http: HttpClient) {}

  create(productDocument: IProductDocument): Observable<EntityResponseType> {
    return this.http.post<IProductDocument>(this.resourceUrl, productDocument, { observe: 'response' });
  }

  update(productDocument: IProductDocument): Observable<EntityResponseType> {
    return this.http.put<IProductDocument>(this.resourceUrl, productDocument, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductDocument[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
