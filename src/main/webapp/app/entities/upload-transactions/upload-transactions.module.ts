import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { UploadTransactionsComponent } from './upload-transactions.component';
import { UploadTransactionsDetailComponent } from './upload-transactions-detail.component';
import { UploadTransactionsUpdateComponent } from './upload-transactions-update.component';
import { UploadTransactionsDeleteDialogComponent } from './upload-transactions-delete-dialog.component';
import { uploadTransactionsRoute } from './upload-transactions.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(uploadTransactionsRoute)],
  declarations: [
    UploadTransactionsComponent,
    UploadTransactionsDetailComponent,
    UploadTransactionsUpdateComponent,
    UploadTransactionsDeleteDialogComponent
  ],
  entryComponents: [UploadTransactionsDeleteDialogComponent]
})
export class EpmwebUploadTransactionsModule {}
