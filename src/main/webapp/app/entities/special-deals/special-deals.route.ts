import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from './special-deals.service';
import { SpecialDealsComponent } from './special-deals.component';
import { SpecialDealsDetailComponent } from './special-deals-detail.component';
import { SpecialDealsUpdateComponent } from './special-deals-update.component';
import { ISpecialDeals } from 'app/shared/model/special-deals.model';

@Injectable({ providedIn: 'root' })
export class SpecialDealsResolve implements Resolve<ISpecialDeals> {
  constructor(private service: SpecialDealsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpecialDeals> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((specialDeals: HttpResponse<SpecialDeals>) => specialDeals.body));
    }
    return of(new SpecialDeals());
  }
}

export const specialDealsRoute: Routes = [
  {
    path: '',
    component: SpecialDealsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.specialDeals.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpecialDealsDetailComponent,
    resolve: {
      specialDeals: SpecialDealsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.specialDeals.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpecialDealsUpdateComponent,
    resolve: {
      specialDeals: SpecialDealsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.specialDeals.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpecialDealsUpdateComponent,
    resolve: {
      specialDeals: SpecialDealsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.specialDeals.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
