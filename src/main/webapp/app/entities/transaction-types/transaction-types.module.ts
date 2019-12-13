import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { TransactionTypesComponent } from './transaction-types.component';
import { TransactionTypesDetailComponent } from './transaction-types-detail.component';
import { TransactionTypesUpdateComponent } from './transaction-types-update.component';
import { TransactionTypesDeleteDialogComponent } from './transaction-types-delete-dialog.component';
import { transactionTypesRoute } from './transaction-types.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(transactionTypesRoute)],
  declarations: [
    TransactionTypesComponent,
    TransactionTypesDetailComponent,
    TransactionTypesUpdateComponent,
    TransactionTypesDeleteDialogComponent
  ],
  entryComponents: [TransactionTypesDeleteDialogComponent]
})
export class EpmwebTransactionTypesModule {}
