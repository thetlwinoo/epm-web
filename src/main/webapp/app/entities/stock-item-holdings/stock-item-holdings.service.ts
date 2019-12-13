import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';

type EntityResponseType = HttpResponse<IStockItemHoldings>;
type EntityArrayResponseType = HttpResponse<IStockItemHoldings[]>;

@Injectable({ providedIn: 'root' })
export class StockItemHoldingsService {
  public resourceUrl = SERVER_API_URL + 'api/stock-item-holdings';

  constructor(protected http: HttpClient) {}

  create(stockItemHoldings: IStockItemHoldings): Observable<EntityResponseType> {
    return this.http.post<IStockItemHoldings>(this.resourceUrl, stockItemHoldings, { observe: 'response' });
  }

  update(stockItemHoldings: IStockItemHoldings): Observable<EntityResponseType> {
    return this.http.put<IStockItemHoldings>(this.resourceUrl, stockItemHoldings, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStockItemHoldings>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStockItemHoldings[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
