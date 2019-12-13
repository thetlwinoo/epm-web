import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from './package-types.service';
import { PackageTypesComponent } from './package-types.component';
import { PackageTypesDetailComponent } from './package-types-detail.component';
import { PackageTypesUpdateComponent } from './package-types-update.component';
import { IPackageTypes } from 'app/shared/model/package-types.model';

@Injectable({ providedIn: 'root' })
export class PackageTypesResolve implements Resolve<IPackageTypes> {
  constructor(private service: PackageTypesService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPackageTypes> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((packageTypes: HttpResponse<PackageTypes>) => packageTypes.body));
    }
    return of(new PackageTypes());
  }
}

export const packageTypesRoute: Routes = [
  {
    path: '',
    component: PackageTypesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.packageTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PackageTypesDetailComponent,
    resolve: {
      packageTypes: PackageTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.packageTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PackageTypesUpdateComponent,
    resolve: {
      packageTypes: PackageTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.packageTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PackageTypesUpdateComponent,
    resolve: {
      packageTypes: PackageTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.packageTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
