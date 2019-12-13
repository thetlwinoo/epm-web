import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UnitMeasure } from 'app/shared/model/unit-measure.model';
import { UnitMeasureService } from './unit-measure.service';
import { UnitMeasureComponent } from './unit-measure.component';
import { UnitMeasureDetailComponent } from './unit-measure-detail.component';
import { UnitMeasureUpdateComponent } from './unit-measure-update.component';
import { IUnitMeasure } from 'app/shared/model/unit-measure.model';

@Injectable({ providedIn: 'root' })
export class UnitMeasureResolve implements Resolve<IUnitMeasure> {
  constructor(private service: UnitMeasureService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnitMeasure> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((unitMeasure: HttpResponse<UnitMeasure>) => unitMeasure.body));
    }
    return of(new UnitMeasure());
  }
}

export const unitMeasureRoute: Routes = [
  {
    path: '',
    component: UnitMeasureComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.unitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UnitMeasureDetailComponent,
    resolve: {
      unitMeasure: UnitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.unitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UnitMeasureUpdateComponent,
    resolve: {
      unitMeasure: UnitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.unitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UnitMeasureUpdateComponent,
    resolve: {
      unitMeasure: UnitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.unitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
