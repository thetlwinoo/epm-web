import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BarcodeTypes } from 'app/shared/model/barcode-types.model';
import { BarcodeTypesService } from './barcode-types.service';
import { BarcodeTypesComponent } from './barcode-types.component';
import { BarcodeTypesDetailComponent } from './barcode-types-detail.component';
import { BarcodeTypesUpdateComponent } from './barcode-types-update.component';
import { IBarcodeTypes } from 'app/shared/model/barcode-types.model';

@Injectable({ providedIn: 'root' })
export class BarcodeTypesResolve implements Resolve<IBarcodeTypes> {
  constructor(private service: BarcodeTypesService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBarcodeTypes> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((barcodeTypes: HttpResponse<BarcodeTypes>) => barcodeTypes.body));
    }
    return of(new BarcodeTypes());
  }
}

export const barcodeTypesRoute: Routes = [
  {
    path: '',
    component: BarcodeTypesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.barcodeTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BarcodeTypesDetailComponent,
    resolve: {
      barcodeTypes: BarcodeTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.barcodeTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BarcodeTypesUpdateComponent,
    resolve: {
      barcodeTypes: BarcodeTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.barcodeTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BarcodeTypesUpdateComponent,
    resolve: {
      barcodeTypes: BarcodeTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.barcodeTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
