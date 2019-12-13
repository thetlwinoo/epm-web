import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ProductSetDetailsComponent } from './product-set-details.component';
import { ProductSetDetailsDetailComponent } from './product-set-details-detail.component';
import { ProductSetDetailsUpdateComponent } from './product-set-details-update.component';
import { ProductSetDetailsDeleteDialogComponent } from './product-set-details-delete-dialog.component';
import { productSetDetailsRoute } from './product-set-details.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(productSetDetailsRoute)],
  declarations: [
    ProductSetDetailsComponent,
    ProductSetDetailsDetailComponent,
    ProductSetDetailsUpdateComponent,
    ProductSetDetailsDeleteDialogComponent
  ],
  entryComponents: [ProductSetDetailsDeleteDialogComponent]
})
export class EpmwebProductSetDetailsModule {}
