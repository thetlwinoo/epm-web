import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ProductOptionComponent } from './product-option.component';
import { ProductOptionDetailComponent } from './product-option-detail.component';
import { ProductOptionUpdateComponent } from './product-option-update.component';
import { ProductOptionDeleteDialogComponent } from './product-option-delete-dialog.component';
import { productOptionRoute } from './product-option.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(productOptionRoute)],
  declarations: [ProductOptionComponent, ProductOptionDetailComponent, ProductOptionUpdateComponent, ProductOptionDeleteDialogComponent],
  entryComponents: [ProductOptionDeleteDialogComponent]
})
export class EpmwebProductOptionModule {}
