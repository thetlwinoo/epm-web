import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWishlists } from 'app/shared/model/wishlists.model';

type EntityResponseType = HttpResponse<IWishlists>;
type EntityArrayResponseType = HttpResponse<IWishlists[]>;

@Injectable({ providedIn: 'root' })
export class WishlistsService {
  public resourceUrl = SERVER_API_URL + 'api/wishlists';

  constructor(protected http: HttpClient) {}

  create(wishlists: IWishlists): Observable<EntityResponseType> {
    return this.http.post<IWishlists>(this.resourceUrl, wishlists, { observe: 'response' });
  }

  update(wishlists: IWishlists): Observable<EntityResponseType> {
    return this.http.put<IWishlists>(this.resourceUrl, wishlists, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWishlists>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWishlists[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
