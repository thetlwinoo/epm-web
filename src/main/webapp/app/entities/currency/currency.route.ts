import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Currency } from 'app/shared/model/currency.model';
import { CurrencyService } from './currency.service';
import { CurrencyComponent } from './currency.component';
import { CurrencyDetailComponent } from './currency-detail.component';
import { CurrencyUpdateComponent } from './currency-update.component';
import { ICurrency } from 'app/shared/model/currency.model';

@Injectable({ providedIn: 'root' })
export class CurrencyResolve implements Resolve<ICurrency> {
  constructor(private service: CurrencyService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICurrency> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((currency: HttpResponse<Currency>) => currency.body));
    }
    return of(new Currency());
  }
}

export const currencyRoute: Routes = [
  {
    path: '',
    component: CurrencyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CurrencyDetailComponent,
    resolve: {
      currency: CurrencyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CurrencyUpdateComponent,
    resolve: {
      currency: CurrencyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CurrencyUpdateComponent,
    resolve: {
      currency: CurrencyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.currency.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
