import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ProductOptionSetComponent } from './product-option-set.component';
import { ProductOptionSetDetailComponent } from './product-option-set-detail.component';
import { ProductOptionSetUpdateComponent } from './product-option-set-update.component';
import { ProductOptionSetDeleteDialogComponent } from './product-option-set-delete-dialog.component';
import { productOptionSetRoute } from './product-option-set.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(productOptionSetRoute)],
  declarations: [
    ProductOptionSetComponent,
    ProductOptionSetDetailComponent,
    ProductOptionSetUpdateComponent,
    ProductOptionSetDeleteDialogComponent
  ],
  entryComponents: [ProductOptionSetDeleteDialogComponent]
})
export class EpmwebProductOptionSetModule {}
