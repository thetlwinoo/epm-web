import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';

type EntityResponseType = HttpResponse<IShoppingCarts>;
type EntityArrayResponseType = HttpResponse<IShoppingCarts[]>;

@Injectable({ providedIn: 'root' })
export class ShoppingCartsService {
  public resourceUrl = SERVER_API_URL + 'api/shopping-carts';

  constructor(protected http: HttpClient) {}

  create(shoppingCarts: IShoppingCarts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shoppingCarts);
    return this.http
      .post<IShoppingCarts>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(shoppingCarts: IShoppingCarts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shoppingCarts);
    return this.http
      .put<IShoppingCarts>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IShoppingCarts>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IShoppingCarts[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(shoppingCarts: IShoppingCarts): IShoppingCarts {
    const copy: IShoppingCarts = Object.assign({}, shoppingCarts, {
      lastEditedWhen:
        shoppingCarts.lastEditedWhen != null && shoppingCarts.lastEditedWhen.isValid() ? shoppingCarts.lastEditedWhen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((shoppingCarts: IShoppingCarts) => {
        shoppingCarts.lastEditedWhen = shoppingCarts.lastEditedWhen != null ? moment(shoppingCarts.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
