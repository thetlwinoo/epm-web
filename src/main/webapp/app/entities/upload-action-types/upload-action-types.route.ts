import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UploadActionTypes } from 'app/shared/model/upload-action-types.model';
import { UploadActionTypesService } from './upload-action-types.service';
import { UploadActionTypesComponent } from './upload-action-types.component';
import { UploadActionTypesDetailComponent } from './upload-action-types-detail.component';
import { UploadActionTypesUpdateComponent } from './upload-action-types-update.component';
import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';

@Injectable({ providedIn: 'root' })
export class UploadActionTypesResolve implements Resolve<IUploadActionTypes> {
  constructor(private service: UploadActionTypesService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUploadActionTypes> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((uploadActionTypes: HttpResponse<UploadActionTypes>) => uploadActionTypes.body));
    }
    return of(new UploadActionTypes());
  }
}

export const uploadActionTypesRoute: Routes = [
  {
    path: '',
    component: UploadActionTypesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.uploadActionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UploadActionTypesDetailComponent,
    resolve: {
      uploadActionTypes: UploadActionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.uploadActionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UploadActionTypesUpdateComponent,
    resolve: {
      uploadActionTypes: UploadActionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.uploadActionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UploadActionTypesUpdateComponent,
    resolve: {
      uploadActionTypes: UploadActionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.uploadActionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
