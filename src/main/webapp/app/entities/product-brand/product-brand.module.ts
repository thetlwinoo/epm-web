import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ProductBrandComponent } from './product-brand.component';
import { ProductBrandDetailComponent } from './product-brand-detail.component';
import { ProductBrandUpdateComponent } from './product-brand-update.component';
import { ProductBrandDeleteDialogComponent } from './product-brand-delete-dialog.component';
import { productBrandRoute } from './product-brand.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(productBrandRoute)],
  declarations: [ProductBrandComponent, ProductBrandDetailComponent, ProductBrandUpdateComponent, ProductBrandDeleteDialogComponent],
  entryComponents: [ProductBrandDeleteDialogComponent]
})
export class EpmwebProductBrandModule {}
