import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { StockItemTransactions } from 'app/shared/model/stock-item-transactions.model';
import { StockItemTransactionsService } from './stock-item-transactions.service';
import { StockItemTransactionsComponent } from './stock-item-transactions.component';
import { StockItemTransactionsDetailComponent } from './stock-item-transactions-detail.component';
import { StockItemTransactionsUpdateComponent } from './stock-item-transactions-update.component';
import { IStockItemTransactions } from 'app/shared/model/stock-item-transactions.model';

@Injectable({ providedIn: 'root' })
export class StockItemTransactionsResolve implements Resolve<IStockItemTransactions> {
  constructor(private service: StockItemTransactionsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStockItemTransactions> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((stockItemTransactions: HttpResponse<StockItemTransactions>) => stockItemTransactions.body));
    }
    return of(new StockItemTransactions());
  }
}

export const stockItemTransactionsRoute: Routes = [
  {
    path: '',
    component: StockItemTransactionsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockItemTransactionsDetailComponent,
    resolve: {
      stockItemTransactions: StockItemTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockItemTransactionsUpdateComponent,
    resolve: {
      stockItemTransactions: StockItemTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockItemTransactionsUpdateComponent,
    resolve: {
      stockItemTransactions: StockItemTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.stockItemTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
