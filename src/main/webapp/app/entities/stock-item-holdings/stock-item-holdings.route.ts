import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { StockItemHoldings } from 'app/shared/model/stock-item-holdings.model';
import { StockItemHoldingsService } from './stock-item-holdings.service';
import { StockItemHoldingsComponent } from './stock-item-holdings.component';
import { StockItemHoldingsDetailComponent } from './stock-item-holdings-detail.component';
import { StockItemHoldingsUpdateComponent } from './stock-item-holdings-update.component';
import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';

@Injectable({ providedIn: 'root' })
export class StockItemHoldingsResolve implements Resolve<IStockItemHoldings> {
  constructor(private service: StockItemHoldingsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStockItemHoldings> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((stockItemHoldings: HttpResponse<StockItemHoldings>) => stockItemHoldings.body));
    }
    return of(new StockItemHoldings());
  }
}

export const stockItemHoldingsRoute: Routes = [
  {
    path: '',
    component: StockItemHoldingsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemHoldings.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockItemHoldingsDetailComponent,
    resolve: {
      stockItemHoldings: StockItemHoldingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemHoldings.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockItemHoldingsUpdateComponent,
    resolve: {
      stockItemHoldings: StockItemHoldingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemHoldings.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockItemHoldingsUpdateComponent,
    resolve: {
      stockItemHoldings: StockItemHoldingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemHoldings.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
