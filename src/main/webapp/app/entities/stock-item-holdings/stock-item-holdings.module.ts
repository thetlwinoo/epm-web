import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { StockItemHoldingsComponent } from './stock-item-holdings.component';
import { StockItemHoldingsDetailComponent } from './stock-item-holdings-detail.component';
import { StockItemHoldingsUpdateComponent } from './stock-item-holdings-update.component';
import { StockItemHoldingsDeleteDialogComponent } from './stock-item-holdings-delete-dialog.component';
import { stockItemHoldingsRoute } from './stock-item-holdings.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(stockItemHoldingsRoute)],
  declarations: [
    StockItemHoldingsComponent,
    StockItemHoldingsDetailComponent,
    StockItemHoldingsUpdateComponent,
    StockItemHoldingsDeleteDialogComponent
  ],
  entryComponents: [StockItemHoldingsDeleteDialogComponent]
})
export class EpmwebStockItemHoldingsModule {}
