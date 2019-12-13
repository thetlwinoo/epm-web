import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from './shopping-carts.service';
import { ShoppingCartsComponent } from './shopping-carts.component';
import { ShoppingCartsDetailComponent } from './shopping-carts-detail.component';
import { ShoppingCartsUpdateComponent } from './shopping-carts-update.component';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';

@Injectable({ providedIn: 'root' })
export class ShoppingCartsResolve implements Resolve<IShoppingCarts> {
  constructor(private service: ShoppingCartsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShoppingCarts> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((shoppingCarts: HttpResponse<ShoppingCarts>) => shoppingCarts.body));
    }
    return of(new ShoppingCarts());
  }
}

export const shoppingCartsRoute: Routes = [
  {
    path: '',
    component: ShoppingCartsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shoppingCarts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ShoppingCartsDetailComponent,
    resolve: {
      shoppingCarts: ShoppingCartsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shoppingCarts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ShoppingCartsUpdateComponent,
    resolve: {
      shoppingCarts: ShoppingCartsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shoppingCarts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ShoppingCartsUpdateComponent,
    resolve: {
      shoppingCarts: ShoppingCartsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shoppingCarts.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
