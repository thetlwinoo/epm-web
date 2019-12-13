import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CurrencyRate } from 'app/shared/model/currency-rate.model';
import { CurrencyRateService } from './currency-rate.service';
import { CurrencyRateComponent } from './currency-rate.component';
import { CurrencyRateDetailComponent } from './currency-rate-detail.component';
import { CurrencyRateUpdateComponent } from './currency-rate-update.component';
import { ICurrencyRate } from 'app/shared/model/currency-rate.model';

@Injectable({ providedIn: 'root' })
export class CurrencyRateResolve implements Resolve<ICurrencyRate> {
  constructor(private service: CurrencyRateService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICurrencyRate> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((currencyRate: HttpResponse<CurrencyRate>) => currencyRate.body));
    }
    return of(new CurrencyRate());
  }
}

export const currencyRateRoute: Routes = [
  {
    path: '',
    component: CurrencyRateComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.currencyRate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CurrencyRateDetailComponent,
    resolve: {
      currencyRate: CurrencyRateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.currencyRate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CurrencyRateUpdateComponent,
    resolve: {
      currencyRate: CurrencyRateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.currencyRate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CurrencyRateUpdateComponent,
    resolve: {
      currencyRate: CurrencyRateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.currencyRate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
