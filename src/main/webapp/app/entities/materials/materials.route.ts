import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Materials } from 'app/shared/model/materials.model';
import { MaterialsService } from './materials.service';
import { MaterialsComponent } from './materials.component';
import { MaterialsDetailComponent } from './materials-detail.component';
import { MaterialsUpdateComponent } from './materials-update.component';
import { IMaterials } from 'app/shared/model/materials.model';

@Injectable({ providedIn: 'root' })
export class MaterialsResolve implements Resolve<IMaterials> {
  constructor(private service: MaterialsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMaterials> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((materials: HttpResponse<Materials>) => materials.body));
    }
    return of(new Materials());
  }
}

export const materialsRoute: Routes = [
  {
    path: '',
    component: MaterialsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.materials.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MaterialsDetailComponent,
    resolve: {
      materials: MaterialsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.materials.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MaterialsUpdateComponent,
    resolve: {
      materials: MaterialsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.materials.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MaterialsUpdateComponent,
    resolve: {
      materials: MaterialsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.materials.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
