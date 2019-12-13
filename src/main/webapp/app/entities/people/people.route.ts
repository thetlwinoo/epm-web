import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { People } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';
import { PeopleComponent } from './people.component';
import { PeopleDetailComponent } from './people-detail.component';
import { PeopleUpdateComponent } from './people-update.component';
import { IPeople } from 'app/shared/model/people.model';

@Injectable({ providedIn: 'root' })
export class PeopleResolve implements Resolve<IPeople> {
  constructor(private service: PeopleService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPeople> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((people: HttpResponse<People>) => people.body));
    }
    return of(new People());
  }
}

export const peopleRoute: Routes = [
  {
    path: '',
    component: PeopleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.people.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PeopleDetailComponent,
    resolve: {
      people: PeopleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.people.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PeopleUpdateComponent,
    resolve: {
      people: PeopleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.people.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PeopleUpdateComponent,
    resolve: {
      people: PeopleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.people.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
