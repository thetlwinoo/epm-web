import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProductOption } from 'app/shared/model/product-option.model';
import { ProductOptionService } from './product-option.service';
import { ProductOptionComponent } from './product-option.component';
import { ProductOptionDetailComponent } from './product-option-detail.component';
import { ProductOptionUpdateComponent } from './product-option-update.component';
import { IProductOption } from 'app/shared/model/product-option.model';

@Injectable({ providedIn: 'root' })
export class ProductOptionResolve implements Resolve<IProductOption> {
  constructor(private service: ProductOptionService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductOption> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((productOption: HttpResponse<ProductOption>) => productOption.body));
    }
    return of(new ProductOption());
  }
}

export const productOptionRoute: Routes = [
  {
    path: '',
    component: ProductOptionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductOptionDetailComponent,
    resolve: {
      productOption: ProductOptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductOptionUpdateComponent,
    resolve: {
      productOption: ProductOptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductOptionUpdateComponent,
    resolve: {
      productOption: ProductOptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
