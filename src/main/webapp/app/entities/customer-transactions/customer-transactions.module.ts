import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { CustomerTransactionsComponent } from './customer-transactions.component';
import { CustomerTransactionsDetailComponent } from './customer-transactions-detail.component';
import { CustomerTransactionsUpdateComponent } from './customer-transactions-update.component';
import { CustomerTransactionsDeleteDialogComponent } from './customer-transactions-delete-dialog.component';
import { customerTransactionsRoute } from './customer-transactions.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(customerTransactionsRoute)],
  declarations: [
    CustomerTransactionsComponent,
    CustomerTransactionsDetailComponent,
    CustomerTransactionsUpdateComponent,
    CustomerTransactionsDeleteDialogComponent
  ],
  entryComponents: [CustomerTransactionsDeleteDialogComponent]
})
export class EpmwebCustomerTransactionsModule {}
