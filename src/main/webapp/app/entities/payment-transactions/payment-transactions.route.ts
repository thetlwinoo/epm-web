import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from './payment-transactions.service';
import { PaymentTransactionsComponent } from './payment-transactions.component';
import { PaymentTransactionsDetailComponent } from './payment-transactions-detail.component';
import { PaymentTransactionsUpdateComponent } from './payment-transactions-update.component';
import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';

@Injectable({ providedIn: 'root' })
export class PaymentTransactionsResolve implements Resolve<IPaymentTransactions> {
  constructor(private service: PaymentTransactionsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentTransactions> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((paymentTransactions: HttpResponse<PaymentTransactions>) => paymentTransactions.body));
    }
    return of(new PaymentTransactions());
  }
}

export const paymentTransactionsRoute: Routes = [
  {
    path: '',
    component: PaymentTransactionsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.paymentTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaymentTransactionsDetailComponent,
    resolve: {
      paymentTransactions: PaymentTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.paymentTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaymentTransactionsUpdateComponent,
    resolve: {
      paymentTransactions: PaymentTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.paymentTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaymentTransactionsUpdateComponent,
    resolve: {
      paymentTransactions: PaymentTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.paymentTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
