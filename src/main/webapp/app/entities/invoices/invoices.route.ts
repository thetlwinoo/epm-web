import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Invoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from './invoices.service';
import { InvoicesComponent } from './invoices.component';
import { InvoicesDetailComponent } from './invoices-detail.component';
import { InvoicesUpdateComponent } from './invoices-update.component';
import { IInvoices } from 'app/shared/model/invoices.model';

@Injectable({ providedIn: 'root' })
export class InvoicesResolve implements Resolve<IInvoices> {
  constructor(private service: InvoicesService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvoices> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((invoices: HttpResponse<Invoices>) => invoices.body));
    }
    return of(new Invoices());
  }
}

export const invoicesRoute: Routes = [
  {
    path: '',
    component: InvoicesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.invoices.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvoicesDetailComponent,
    resolve: {
      invoices: InvoicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.invoices.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvoicesUpdateComponent,
    resolve: {
      invoices: InvoicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.invoices.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvoicesUpdateComponent,
    resolve: {
      invoices: InvoicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.invoices.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
