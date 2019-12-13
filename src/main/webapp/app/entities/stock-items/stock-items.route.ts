import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { StockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from './stock-items.service';
import { StockItemsComponent } from './stock-items.component';
import { StockItemsDetailComponent } from './stock-items-detail.component';
import { StockItemsUpdateComponent } from './stock-items-update.component';
import { IStockItems } from 'app/shared/model/stock-items.model';

@Injectable({ providedIn: 'root' })
export class StockItemsResolve implements Resolve<IStockItems> {
  constructor(private service: StockItemsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStockItems> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((stockItems: HttpResponse<StockItems>) => stockItems.body));
    }
    return of(new StockItems());
  }
}

export const stockItemsRoute: Routes = [
  {
    path: '',
    component: StockItemsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'epmwebApp.stockItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockItemsDetailComponent,
    resolve: {
      stockItems: StockItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockItemsUpdateComponent,
    resolve: {
      stockItems: StockItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockItemsUpdateComponent,
    resolve: {
      stockItems: StockItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
