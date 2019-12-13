import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Addresses } from 'app/shared/model/addresses.model';
import { AddressesService } from './addresses.service';
import { AddressesComponent } from './addresses.component';
import { AddressesDetailComponent } from './addresses-detail.component';
import { AddressesUpdateComponent } from './addresses-update.component';
import { IAddresses } from 'app/shared/model/addresses.model';

@Injectable({ providedIn: 'root' })
export class AddressesResolve implements Resolve<IAddresses> {
  constructor(private service: AddressesService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAddresses> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((addresses: HttpResponse<Addresses>) => addresses.body));
    }
    return of(new Addresses());
  }
}

export const addressesRoute: Routes = [
  {
    path: '',
    component: AddressesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.addresses.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AddressesDetailComponent,
    resolve: {
      addresses: AddressesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.addresses.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AddressesUpdateComponent,
    resolve: {
      addresses: AddressesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.addresses.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AddressesUpdateComponent,
    resolve: {
      addresses: AddressesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.addresses.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
