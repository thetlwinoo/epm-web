import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { StockItemTransactionsComponent } from './stock-item-transactions.component';
import { StockItemTransactionsDetailComponent } from './stock-item-transactions-detail.component';
import { StockItemTransactionsUpdateComponent } from './stock-item-transactions-update.component';
import { StockItemTransactionsDeleteDialogComponent } from './stock-item-transactions-delete-dialog.component';
import { stockItemTransactionsRoute } from './stock-item-transactions.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(stockItemTransactionsRoute)],
  declarations: [
    StockItemTransactionsComponent,
    StockItemTransactionsDetailComponent,
    StockItemTransactionsUpdateComponent,
    StockItemTransactionsDeleteDialogComponent
  ],
  entryComponents: [StockItemTransactionsDeleteDialogComponent]
})
export class EpmwebStockItemTransactionsModule {}
