import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProductCatalog } from 'app/shared/model/product-catalog.model';
import { ProductCatalogService } from './product-catalog.service';
import { ProductCatalogComponent } from './product-catalog.component';
import { ProductCatalogDetailComponent } from './product-catalog-detail.component';
import { ProductCatalogUpdateComponent } from './product-catalog-update.component';
import { IProductCatalog } from 'app/shared/model/product-catalog.model';

@Injectable({ providedIn: 'root' })
export class ProductCatalogResolve implements Resolve<IProductCatalog> {
  constructor(private service: ProductCatalogService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductCatalog> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((productCatalog: HttpResponse<ProductCatalog>) => productCatalog.body));
    }
    return of(new ProductCatalog());
  }
}

export const productCatalogRoute: Routes = [
  {
    path: '',
    component: ProductCatalogComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productCatalog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductCatalogDetailComponent,
    resolve: {
      productCatalog: ProductCatalogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productCatalog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductCatalogUpdateComponent,
    resolve: {
      productCatalog: ProductCatalogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productCatalog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductCatalogUpdateComponent,
    resolve: {
      productCatalog: ProductCatalogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productCatalog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
