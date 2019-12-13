import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { PurchaseOrderLinesComponent } from './purchase-order-lines.component';
import { PurchaseOrderLinesDetailComponent } from './purchase-order-lines-detail.component';
import { PurchaseOrderLinesUpdateComponent } from './purchase-order-lines-update.component';
import { PurchaseOrderLinesDeleteDialogComponent } from './purchase-order-lines-delete-dialog.component';
import { purchaseOrderLinesRoute } from './purchase-order-lines.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(purchaseOrderLinesRoute)],
  declarations: [
    PurchaseOrderLinesComponent,
    PurchaseOrderLinesDetailComponent,
    PurchaseOrderLinesUpdateComponent,
    PurchaseOrderLinesDeleteDialogComponent
  ],
  entryComponents: [PurchaseOrderLinesDeleteDialogComponent]
})
export class EpmwebPurchaseOrderLinesModule {}
