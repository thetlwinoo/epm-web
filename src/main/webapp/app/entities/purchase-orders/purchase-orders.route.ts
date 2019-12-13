import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from './purchase-orders.service';
import { PurchaseOrdersComponent } from './purchase-orders.component';
import { PurchaseOrdersDetailComponent } from './purchase-orders-detail.component';
import { PurchaseOrdersUpdateComponent } from './purchase-orders-update.component';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';

@Injectable({ providedIn: 'root' })
export class PurchaseOrdersResolve implements Resolve<IPurchaseOrders> {
  constructor(private service: PurchaseOrdersService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPurchaseOrders> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((purchaseOrders: HttpResponse<PurchaseOrders>) => purchaseOrders.body));
    }
    return of(new PurchaseOrders());
  }
}

export const purchaseOrdersRoute: Routes = [
  {
    path: '',
    component: PurchaseOrdersComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.purchaseOrders.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PurchaseOrdersDetailComponent,
    resolve: {
      purchaseOrders: PurchaseOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.purchaseOrders.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PurchaseOrdersUpdateComponent,
    resolve: {
      purchaseOrders: PurchaseOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.purchaseOrders.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PurchaseOrdersUpdateComponent,
    resolve: {
      purchaseOrders: PurchaseOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.purchaseOrders.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
