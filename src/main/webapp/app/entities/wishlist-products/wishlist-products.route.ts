import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { WishlistProducts } from 'app/shared/model/wishlist-products.model';
import { WishlistProductsService } from './wishlist-products.service';
import { WishlistProductsComponent } from './wishlist-products.component';
import { WishlistProductsDetailComponent } from './wishlist-products-detail.component';
import { WishlistProductsUpdateComponent } from './wishlist-products-update.component';
import { IWishlistProducts } from 'app/shared/model/wishlist-products.model';

@Injectable({ providedIn: 'root' })
export class WishlistProductsResolve implements Resolve<IWishlistProducts> {
  constructor(private service: WishlistProductsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWishlistProducts> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((wishlistProducts: HttpResponse<WishlistProducts>) => wishlistProducts.body));
    }
    return of(new WishlistProducts());
  }
}

export const wishlistProductsRoute: Routes = [
  {
    path: '',
    component: WishlistProductsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.wishlistProducts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WishlistProductsDetailComponent,
    resolve: {
      wishlistProducts: WishlistProductsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.wishlistProducts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WishlistProductsUpdateComponent,
    resolve: {
      wishlistProducts: WishlistProductsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.wishlistProducts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WishlistProductsUpdateComponent,
    resolve: {
      wishlistProducts: WishlistProductsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.wishlistProducts.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
