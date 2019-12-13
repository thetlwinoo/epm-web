import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';
import { ShoppingCartItemsService } from './shopping-cart-items.service';
import { ShoppingCartItemsComponent } from './shopping-cart-items.component';
import { ShoppingCartItemsDetailComponent } from './shopping-cart-items-detail.component';
import { ShoppingCartItemsUpdateComponent } from './shopping-cart-items-update.component';
import { IShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';

@Injectable({ providedIn: 'root' })
export class ShoppingCartItemsResolve implements Resolve<IShoppingCartItems> {
  constructor(private service: ShoppingCartItemsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShoppingCartItems> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((shoppingCartItems: HttpResponse<ShoppingCartItems>) => shoppingCartItems.body));
    }
    return of(new ShoppingCartItems());
  }
}

export const shoppingCartItemsRoute: Routes = [
  {
    path: '',
    component: ShoppingCartItemsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shoppingCartItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ShoppingCartItemsDetailComponent,
    resolve: {
      shoppingCartItems: ShoppingCartItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shoppingCartItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ShoppingCartItemsUpdateComponent,
    resolve: {
      shoppingCartItems: ShoppingCartItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shoppingCartItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ShoppingCartItemsUpdateComponent,
    resolve: {
      shoppingCartItems: ShoppingCartItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shoppingCartItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
