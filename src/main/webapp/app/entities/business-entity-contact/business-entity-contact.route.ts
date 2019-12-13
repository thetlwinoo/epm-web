import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BusinessEntityContact } from 'app/shared/model/business-entity-contact.model';
import { BusinessEntityContactService } from './business-entity-contact.service';
import { BusinessEntityContactComponent } from './business-entity-contact.component';
import { BusinessEntityContactDetailComponent } from './business-entity-contact-detail.component';
import { BusinessEntityContactUpdateComponent } from './business-entity-contact-update.component';
import { IBusinessEntityContact } from 'app/shared/model/business-entity-contact.model';

@Injectable({ providedIn: 'root' })
export class BusinessEntityContactResolve implements Resolve<IBusinessEntityContact> {
  constructor(private service: BusinessEntityContactService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBusinessEntityContact> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((businessEntityContact: HttpResponse<BusinessEntityContact>) => businessEntityContact.body));
    }
    return of(new BusinessEntityContact());
  }
}

export const businessEntityContactRoute: Routes = [
  {
    path: '',
    component: BusinessEntityContactComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.businessEntityContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BusinessEntityContactDetailComponent,
    resolve: {
      businessEntityContact: BusinessEntityContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.businessEntityContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BusinessEntityContactUpdateComponent,
    resolve: {
      businessEntityContact: BusinessEntityContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.businessEntityContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BusinessEntityContactUpdateComponent,
    resolve: {
      businessEntityContact: BusinessEntityContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.businessEntityContact.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
