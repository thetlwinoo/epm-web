import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { PaymentTransactionsComponent } from './payment-transactions.component';
import { PaymentTransactionsDetailComponent } from './payment-transactions-detail.component';
import { PaymentTransactionsUpdateComponent } from './payment-transactions-update.component';
import { PaymentTransactionsDeleteDialogComponent } from './payment-transactions-delete-dialog.component';
import { paymentTransactionsRoute } from './payment-transactions.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(paymentTransactionsRoute)],
  declarations: [
    PaymentTransactionsComponent,
    PaymentTransactionsDetailComponent,
    PaymentTransactionsUpdateComponent,
    PaymentTransactionsDeleteDialogComponent
  ],
  entryComponents: [PaymentTransactionsDeleteDialogComponent]
})
export class EpmwebPaymentTransactionsModule {}
