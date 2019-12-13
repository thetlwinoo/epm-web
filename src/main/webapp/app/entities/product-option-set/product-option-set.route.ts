import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from './product-option-set.service';
import { ProductOptionSetComponent } from './product-option-set.component';
import { ProductOptionSetDetailComponent } from './product-option-set-detail.component';
import { ProductOptionSetUpdateComponent } from './product-option-set-update.component';
import { IProductOptionSet } from 'app/shared/model/product-option-set.model';

@Injectable({ providedIn: 'root' })
export class ProductOptionSetResolve implements Resolve<IProductOptionSet> {
  constructor(private service: ProductOptionSetService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductOptionSet> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((productOptionSet: HttpResponse<ProductOptionSet>) => productOptionSet.body));
    }
    return of(new ProductOptionSet());
  }
}

export const productOptionSetRoute: Routes = [
  {
    path: '',
    component: ProductOptionSetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductOptionSetDetailComponent,
    resolve: {
      productOptionSet: ProductOptionSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductOptionSetUpdateComponent,
    resolve: {
      productOptionSet: ProductOptionSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductOptionSetUpdateComponent,
    resolve: {
      productOptionSet: ProductOptionSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.productOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
