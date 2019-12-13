import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { StockItemTempComponent } from './stock-item-temp.component';
import { StockItemTempDetailComponent } from './stock-item-temp-detail.component';
import { StockItemTempUpdateComponent } from './stock-item-temp-update.component';
import { StockItemTempDeleteDialogComponent } from './stock-item-temp-delete-dialog.component';
import { stockItemTempRoute } from './stock-item-temp.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(stockItemTempRoute)],
  declarations: [StockItemTempComponent, StockItemTempDetailComponent, StockItemTempUpdateComponent, StockItemTempDeleteDialogComponent],
  entryComponents: [StockItemTempDeleteDialogComponent]
})
export class EpmwebStockItemTempModule {}
