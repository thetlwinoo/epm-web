import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { SupplierImportedDocumentComponent } from './supplier-imported-document.component';
import { SupplierImportedDocumentDetailComponent } from './supplier-imported-document-detail.component';
import { SupplierImportedDocumentUpdateComponent } from './supplier-imported-document-update.component';
import { SupplierImportedDocumentDeleteDialogComponent } from './supplier-imported-document-delete-dialog.component';
import { supplierImportedDocumentRoute } from './supplier-imported-document.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(supplierImportedDocumentRoute)],
  declarations: [
    SupplierImportedDocumentComponent,
    SupplierImportedDocumentDetailComponent,
    SupplierImportedDocumentUpdateComponent,
    SupplierImportedDocumentDeleteDialogComponent
  ],
  entryComponents: [SupplierImportedDocumentDeleteDialogComponent]
})
export class EpmwebSupplierImportedDocumentModule {}
