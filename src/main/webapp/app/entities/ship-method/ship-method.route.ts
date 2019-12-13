import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ShipMethod } from 'app/shared/model/ship-method.model';
import { ShipMethodService } from './ship-method.service';
import { ShipMethodComponent } from './ship-method.component';
import { ShipMethodDetailComponent } from './ship-method-detail.component';
import { ShipMethodUpdateComponent } from './ship-method-update.component';
import { IShipMethod } from 'app/shared/model/ship-method.model';

@Injectable({ providedIn: 'root' })
export class ShipMethodResolve implements Resolve<IShipMethod> {
  constructor(private service: ShipMethodService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShipMethod> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((shipMethod: HttpResponse<ShipMethod>) => shipMethod.body));
    }
    return of(new ShipMethod());
  }
}

export const shipMethodRoute: Routes = [
  {
    path: '',
    component: ShipMethodComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shipMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ShipMethodDetailComponent,
    resolve: {
      shipMethod: ShipMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shipMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ShipMethodUpdateComponent,
    resolve: {
      shipMethod: ShipMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shipMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ShipMethodUpdateComponent,
    resolve: {
      shipMethod: ShipMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.shipMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
