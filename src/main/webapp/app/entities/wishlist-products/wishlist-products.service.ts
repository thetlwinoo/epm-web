import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWishlistProducts } from 'app/shared/model/wishlist-products.model';

type EntityResponseType = HttpResponse<IWishlistProducts>;
type EntityArrayResponseType = HttpResponse<IWishlistProducts[]>;

@Injectable({ providedIn: 'root' })
export class WishlistProductsService {
  public resourceUrl = SERVER_API_URL + 'api/wishlist-products';

  constructor(protected http: HttpClient) {}

  create(wishlistProducts: IWishlistProducts): Observable<EntityResponseType> {
    return this.http.post<IWishlistProducts>(this.resourceUrl, wishlistProducts, { observe: 'response' });
  }

  update(wishlistProducts: IWishlistProducts): Observable<EntityResponseType> {
    return this.http.put<IWishlistProducts>(this.resourceUrl, wishlistProducts, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWishlistProducts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWishlistProducts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
