import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from './product-brand.service';
import { ProductBrandComponent } from './product-brand.component';
import { ProductBrandDetailComponent } from './product-brand-detail.component';
import { ProductBrandUpdateComponent } from './product-brand-update.component';
import { IProductBrand } from 'app/shared/model/product-brand.model';

@Injectable({ providedIn: 'root' })
export class ProductBrandResolve implements Resolve<IProductBrand> {
  constructor(private service: ProductBrandService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductBrand> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((productBrand: HttpResponse<ProductBrand>) => productBrand.body));
    }
    return of(new ProductBrand());
  }
}

export const productBrandRoute: Routes = [
  {
    path: '',
    component: ProductBrandComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productBrand.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductBrandDetailComponent,
    resolve: {
      productBrand: ProductBrandResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productBrand.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductBrandUpdateComponent,
    resolve: {
      productBrand: ProductBrandResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productBrand.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductBrandUpdateComponent,
    resolve: {
      productBrand: ProductBrandResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productBrand.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
