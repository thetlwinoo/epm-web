import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IShipMethod } from 'app/shared/model/ship-method.model';

type EntityResponseType = HttpResponse<IShipMethod>;
type EntityArrayResponseType = HttpResponse<IShipMethod[]>;

@Injectable({ providedIn: 'root' })
export class ShipMethodService {
  public resourceUrl = SERVER_API_URL + 'api/ship-methods';

  constructor(protected http: HttpClient) {}

  create(shipMethod: IShipMethod): Observable<EntityResponseType> {
    return this.http.post<IShipMethod>(this.resourceUrl, shipMethod, { observe: 'response' });
  }

  update(shipMethod: IShipMethod): Observable<EntityResponseType> {
    return this.http.put<IShipMethod>(this.resourceUrl, shipMethod, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShipMethod>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShipMethod[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
