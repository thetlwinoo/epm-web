import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';
import { PurchaseOrderLinesService } from './purchase-order-lines.service';
import { PurchaseOrderLinesComponent } from './purchase-order-lines.component';
import { PurchaseOrderLinesDetailComponent } from './purchase-order-lines-detail.component';
import { PurchaseOrderLinesUpdateComponent } from './purchase-order-lines-update.component';
import { IPurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';

@Injectable({ providedIn: 'root' })
export class PurchaseOrderLinesResolve implements Resolve<IPurchaseOrderLines> {
  constructor(private service: PurchaseOrderLinesService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPurchaseOrderLines> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((purchaseOrderLines: HttpResponse<PurchaseOrderLines>) => purchaseOrderLines.body));
    }
    return of(new PurchaseOrderLines());
  }
}

export const purchaseOrderLinesRoute: Routes = [
  {
    path: '',
    component: PurchaseOrderLinesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.purchaseOrderLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PurchaseOrderLinesDetailComponent,
    resolve: {
      purchaseOrderLines: PurchaseOrderLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.purchaseOrderLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PurchaseOrderLinesUpdateComponent,
    resolve: {
      purchaseOrderLines: PurchaseOrderLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.purchaseOrderLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PurchaseOrderLinesUpdateComponent,
    resolve: {
      purchaseOrderLines: PurchaseOrderLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.purchaseOrderLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
