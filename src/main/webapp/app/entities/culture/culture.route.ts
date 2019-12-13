import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Culture } from 'app/shared/model/culture.model';
import { CultureService } from './culture.service';
import { CultureComponent } from './culture.component';
import { CultureDetailComponent } from './culture-detail.component';
import { CultureUpdateComponent } from './culture-update.component';
import { ICulture } from 'app/shared/model/culture.model';

@Injectable({ providedIn: 'root' })
export class CultureResolve implements Resolve<ICulture> {
  constructor(private service: CultureService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICulture> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((culture: HttpResponse<Culture>) => culture.body));
    }
    return of(new Culture());
  }
}

export const cultureRoute: Routes = [
  {
    path: '',
    component: CultureComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.culture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CultureDetailComponent,
    resolve: {
      culture: CultureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.culture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CultureUpdateComponent,
    resolve: {
      culture: CultureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.culture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CultureUpdateComponent,
    resolve: {
      culture: CultureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.culture.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
