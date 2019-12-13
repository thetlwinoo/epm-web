import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ProductDocumentComponent } from './product-document.component';
import { ProductDocumentDetailComponent } from './product-document-detail.component';
import { ProductDocumentUpdateComponent } from './product-document-update.component';
import { ProductDocumentDeleteDialogComponent } from './product-document-delete-dialog.component';
import { productDocumentRoute } from './product-document.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(productDocumentRoute)],
  declarations: [
    ProductDocumentComponent,
    ProductDocumentDetailComponent,
    ProductDocumentUpdateComponent,
    ProductDocumentDeleteDialogComponent
  ],
  entryComponents: [ProductDocumentDeleteDialogComponent]
})
export class EpmwebProductDocumentModule {}
