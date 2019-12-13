import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from './delivery-methods.service';
import { DeliveryMethodsComponent } from './delivery-methods.component';
import { DeliveryMethodsDetailComponent } from './delivery-methods-detail.component';
import { DeliveryMethodsUpdateComponent } from './delivery-methods-update.component';
import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';

@Injectable({ providedIn: 'root' })
export class DeliveryMethodsResolve implements Resolve<IDeliveryMethods> {
  constructor(private service: DeliveryMethodsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeliveryMethods> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((deliveryMethods: HttpResponse<DeliveryMethods>) => deliveryMethods.body));
    }
    return of(new DeliveryMethods());
  }
}

export const deliveryMethodsRoute: Routes = [
  {
    path: '',
    component: DeliveryMethodsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.deliveryMethods.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeliveryMethodsDetailComponent,
    resolve: {
      deliveryMethods: DeliveryMethodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.deliveryMethods.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeliveryMethodsUpdateComponent,
    resolve: {
      deliveryMethods: DeliveryMethodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.deliveryMethods.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeliveryMethodsUpdateComponent,
    resolve: {
      deliveryMethods: DeliveryMethodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.deliveryMethods.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
