import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Products } from 'app/shared/model/products.model';
import { ProductsService } from './products.service';
import { ProductsComponent } from './products.component';
import { ProductsDetailComponent } from './products-detail.component';
import { ProductsUpdateComponent } from './products-update.component';
import { IProducts } from 'app/shared/model/products.model';

@Injectable({ providedIn: 'root' })
export class ProductsResolve implements Resolve<IProducts> {
  constructor(private service: ProductsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProducts> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((products: HttpResponse<Products>) => products.body));
    }
    return of(new Products());
  }
}

export const productsRoute: Routes = [
  {
    path: '',
    component: ProductsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'epmwebApp.products.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductsDetailComponent,
    resolve: {
      products: ProductsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.products.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductsUpdateComponent,
    resolve: {
      products: ProductsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.products.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductsUpdateComponent,
    resolve: {
      products: ProductsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.products.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
