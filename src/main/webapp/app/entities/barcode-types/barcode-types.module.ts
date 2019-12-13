import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { BarcodeTypesComponent } from './barcode-types.component';
import { BarcodeTypesDetailComponent } from './barcode-types-detail.component';
import { BarcodeTypesUpdateComponent } from './barcode-types-update.component';
import { BarcodeTypesDeleteDialogComponent } from './barcode-types-delete-dialog.component';
import { barcodeTypesRoute } from './barcode-types.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(barcodeTypesRoute)],
  declarations: [BarcodeTypesComponent, BarcodeTypesDetailComponent, BarcodeTypesUpdateComponent, BarcodeTypesDeleteDialogComponent],
  entryComponents: [BarcodeTypesDeleteDialogComponent]
})
export class EpmwebBarcodeTypesModule {}
