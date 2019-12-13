import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Photos } from 'app/shared/model/photos.model';
import { PhotosService } from './photos.service';
import { PhotosComponent } from './photos.component';
import { PhotosDetailComponent } from './photos-detail.component';
import { PhotosUpdateComponent } from './photos-update.component';
import { IPhotos } from 'app/shared/model/photos.model';

@Injectable({ providedIn: 'root' })
export class PhotosResolve implements Resolve<IPhotos> {
  constructor(private service: PhotosService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhotos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((photos: HttpResponse<Photos>) => photos.body));
    }
    return of(new Photos());
  }
}

export const photosRoute: Routes = [
  {
    path: '',
    component: PhotosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.photos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PhotosDetailComponent,
    resolve: {
      photos: PhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.photos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PhotosUpdateComponent,
    resolve: {
      photos: PhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.photos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PhotosUpdateComponent,
    resolve: {
      photos: PhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.photos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
