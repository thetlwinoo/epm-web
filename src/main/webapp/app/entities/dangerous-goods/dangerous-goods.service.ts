import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDangerousGoods } from 'app/shared/model/dangerous-goods.model';

type EntityResponseType = HttpResponse<IDangerousGoods>;
type EntityArrayResponseType = HttpResponse<IDangerousGoods[]>;

@Injectable({ providedIn: 'root' })
export class DangerousGoodsService {
  public resourceUrl = SERVER_API_URL + 'api/dangerous-goods';

  constructor(protected http: HttpClient) {}

  create(dangerousGoods: IDangerousGoods): Observable<EntityResponseType> {
    return this.http.post<IDangerousGoods>(this.resourceUrl, dangerousGoods, { observe: 'response' });
  }

  update(dangerousGoods: IDangerousGoods): Observable<EntityResponseType> {
    return this.http.put<IDangerousGoods>(this.resourceUrl, dangerousGoods, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDangerousGoods>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDangerousGoods[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
