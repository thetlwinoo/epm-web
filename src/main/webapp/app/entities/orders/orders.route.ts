import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Orders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';
import { OrdersComponent } from './orders.component';
import { OrdersDetailComponent } from './orders-detail.component';
import { OrdersUpdateComponent } from './orders-update.component';
import { IOrders } from 'app/shared/model/orders.model';

@Injectable({ providedIn: 'root' })
export class OrdersResolve implements Resolve<IOrders> {
  constructor(private service: OrdersService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrders> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((orders: HttpResponse<Orders>) => orders.body));
    }
    return of(new Orders());
  }
}

export const ordersRoute: Routes = [
  {
    path: '',
    component: OrdersComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'epmwebApp.orders.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrdersDetailComponent,
    resolve: {
      orders: OrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.orders.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrdersUpdateComponent,
    resolve: {
      orders: OrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.orders.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrdersUpdateComponent,
    resolve: {
      orders: OrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.orders.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
