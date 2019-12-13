import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BuyingGroups } from 'app/shared/model/buying-groups.model';
import { BuyingGroupsService } from './buying-groups.service';
import { BuyingGroupsComponent } from './buying-groups.component';
import { BuyingGroupsDetailComponent } from './buying-groups-detail.component';
import { BuyingGroupsUpdateComponent } from './buying-groups-update.component';
import { IBuyingGroups } from 'app/shared/model/buying-groups.model';

@Injectable({ providedIn: 'root' })
export class BuyingGroupsResolve implements Resolve<IBuyingGroups> {
  constructor(private service: BuyingGroupsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBuyingGroups> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((buyingGroups: HttpResponse<BuyingGroups>) => buyingGroups.body));
    }
    return of(new BuyingGroups());
  }
}

export const buyingGroupsRoute: Routes = [
  {
    path: '',
    component: BuyingGroupsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.buyingGroups.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BuyingGroupsDetailComponent,
    resolve: {
      buyingGroups: BuyingGroupsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.buyingGroups.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BuyingGroupsUpdateComponent,
    resolve: {
      buyingGroups: BuyingGroupsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.buyingGroups.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BuyingGroupsUpdateComponent,
    resolve: {
      buyingGroups: BuyingGroupsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.buyingGroups.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
