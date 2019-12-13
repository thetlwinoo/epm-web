import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ProductChoiceComponent } from './product-choice.component';
import { ProductChoiceDetailComponent } from './product-choice-detail.component';
import { ProductChoiceUpdateComponent } from './product-choice-update.component';
import { ProductChoiceDeleteDialogComponent } from './product-choice-delete-dialog.component';
import { productChoiceRoute } from './product-choice.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(productChoiceRoute)],
  declarations: [ProductChoiceComponent, ProductChoiceDetailComponent, ProductChoiceUpdateComponent, ProductChoiceDeleteDialogComponent],
  entryComponents: [ProductChoiceDeleteDialogComponent]
})
export class EpmwebProductChoiceModule {}
