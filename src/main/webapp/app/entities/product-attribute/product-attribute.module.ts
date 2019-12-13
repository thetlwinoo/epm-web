import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ProductAttributeComponent } from './product-attribute.component';
import { ProductAttributeDetailComponent } from './product-attribute-detail.component';
import { ProductAttributeUpdateComponent } from './product-attribute-update.component';
import { ProductAttributeDeleteDialogComponent } from './product-attribute-delete-dialog.component';
import { productAttributeRoute } from './product-attribute.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(productAttributeRoute)],
  declarations: [
    ProductAttributeComponent,
    ProductAttributeDetailComponent,
    ProductAttributeUpdateComponent,
    ProductAttributeDeleteDialogComponent
  ],
  entryComponents: [ProductAttributeDeleteDialogComponent]
})
export class EpmwebProductAttributeModule {}
