import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Customers } from 'app/shared/model/customers.model';
import { CustomersService } from './customers.service';
import { CustomersComponent } from './customers.component';
import { CustomersDetailComponent } from './customers-detail.component';
import { CustomersUpdateComponent } from './customers-update.component';
import { ICustomers } from 'app/shared/model/customers.model';

@Injectable({ providedIn: 'root' })
export class CustomersResolve implements Resolve<ICustomers> {
  constructor(private service: CustomersService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomers> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((customers: HttpResponse<Customers>) => customers.body));
    }
    return of(new Customers());
  }
}

export const customersRoute: Routes = [
  {
    path: '',
    component: CustomersComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.customers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CustomersDetailComponent,
    resolve: {
      customers: CustomersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.customers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CustomersUpdateComponent,
    resolve: {
      customers: CustomersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.customers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CustomersUpdateComponent,
    resolve: {
      customers: CustomersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.customers.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
