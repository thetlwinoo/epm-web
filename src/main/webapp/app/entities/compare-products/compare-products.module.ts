import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { CompareProductsComponent } from './compare-products.component';
import { CompareProductsDetailComponent } from './compare-products-detail.component';
import { CompareProductsUpdateComponent } from './compare-products-update.component';
import { CompareProductsDeleteDialogComponent } from './compare-products-delete-dialog.component';
import { compareProductsRoute } from './compare-products.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(compareProductsRoute)],
  declarations: [
    CompareProductsComponent,
    CompareProductsDetailComponent,
    CompareProductsUpdateComponent,
    CompareProductsDeleteDialogComponent
  ],
  entryComponents: [CompareProductsDeleteDialogComponent]
})
export class EpmwebCompareProductsModule {}
