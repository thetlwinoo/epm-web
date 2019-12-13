import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CustomerTransactions } from 'app/shared/model/customer-transactions.model';
import { CustomerTransactionsService } from './customer-transactions.service';
import { CustomerTransactionsComponent } from './customer-transactions.component';
import { CustomerTransactionsDetailComponent } from './customer-transactions-detail.component';
import { CustomerTransactionsUpdateComponent } from './customer-transactions-update.component';
import { ICustomerTransactions } from 'app/shared/model/customer-transactions.model';

@Injectable({ providedIn: 'root' })
export class CustomerTransactionsResolve implements Resolve<ICustomerTransactions> {
  constructor(private service: CustomerTransactionsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerTransactions> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((customerTransactions: HttpResponse<CustomerTransactions>) => customerTransactions.body));
    }
    return of(new CustomerTransactions());
  }
}

export const customerTransactionsRoute: Routes = [
  {
    path: '',
    component: CustomerTransactionsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.customerTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CustomerTransactionsDetailComponent,
    resolve: {
      customerTransactions: CustomerTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.customerTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CustomerTransactionsUpdateComponent,
    resolve: {
      customerTransactions: CustomerTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.customerTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CustomerTransactionsUpdateComponent,
    resolve: {
      customerTransactions: CustomerTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.customerTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
