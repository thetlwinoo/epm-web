import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { StockItemTemp } from 'app/shared/model/stock-item-temp.model';
import { StockItemTempService } from './stock-item-temp.service';
import { StockItemTempComponent } from './stock-item-temp.component';
import { StockItemTempDetailComponent } from './stock-item-temp-detail.component';
import { StockItemTempUpdateComponent } from './stock-item-temp-update.component';
import { IStockItemTemp } from 'app/shared/model/stock-item-temp.model';

@Injectable({ providedIn: 'root' })
export class StockItemTempResolve implements Resolve<IStockItemTemp> {
  constructor(private service: StockItemTempService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStockItemTemp> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((stockItemTemp: HttpResponse<StockItemTemp>) => stockItemTemp.body));
    }
    return of(new StockItemTemp());
  }
}

export const stockItemTempRoute: Routes = [
  {
    path: '',
    component: StockItemTempComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'epmwebApp.stockItemTemp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockItemTempDetailComponent,
    resolve: {
      stockItemTemp: StockItemTempResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemTemp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockItemTempUpdateComponent,
    resolve: {
      stockItemTemp: StockItemTempResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemTemp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockItemTempUpdateComponent,
    resolve: {
      stockItemTemp: StockItemTempResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemTemp.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
