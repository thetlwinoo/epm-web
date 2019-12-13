import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';
import { SupplierImportedDocumentService } from './supplier-imported-document.service';
import { SupplierImportedDocumentComponent } from './supplier-imported-document.component';
import { SupplierImportedDocumentDetailComponent } from './supplier-imported-document-detail.component';
import { SupplierImportedDocumentUpdateComponent } from './supplier-imported-document-update.component';
import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';

@Injectable({ providedIn: 'root' })
export class SupplierImportedDocumentResolve implements Resolve<ISupplierImportedDocument> {
  constructor(private service: SupplierImportedDocumentService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISupplierImportedDocument> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((supplierImportedDocument: HttpResponse<SupplierImportedDocument>) => supplierImportedDocument.body));
    }
    return of(new SupplierImportedDocument());
  }
}

export const supplierImportedDocumentRoute: Routes = [
  {
    path: '',
    component: SupplierImportedDocumentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.supplierImportedDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SupplierImportedDocumentDetailComponent,
    resolve: {
      supplierImportedDocument: SupplierImportedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.supplierImportedDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SupplierImportedDocumentUpdateComponent,
    resolve: {
      supplierImportedDocument: SupplierImportedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.supplierImportedDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SupplierImportedDocumentUpdateComponent,
    resolve: {
      supplierImportedDocument: SupplierImportedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'epmwebApp.supplierImportedDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
