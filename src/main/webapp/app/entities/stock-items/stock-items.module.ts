import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { StockItemsComponent } from './stock-items.component';
import { StockItemsDetailComponent } from './stock-items-detail.component';
import { StockItemsUpdateComponent } from './stock-items-update.component';
import { StockItemsDeleteDialogComponent } from './stock-items-delete-dialog.component';
import { stockItemsRoute } from './stock-items.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(stockItemsRoute)],
  declarations: [StockItemsComponent, StockItemsDetailComponent, StockItemsUpdateComponent, StockItemsDeleteDialogComponent],
  entryComponents: [StockItemsDeleteDialogComponent]
})
export class EpmwebStockItemsModule {}
