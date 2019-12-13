import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { InvoiceLinesComponent } from './invoice-lines.component';
import { InvoiceLinesDetailComponent } from './invoice-lines-detail.component';
import { InvoiceLinesUpdateComponent } from './invoice-lines-update.component';
import { InvoiceLinesDeleteDialogComponent } from './invoice-lines-delete-dialog.component';
import { invoiceLinesRoute } from './invoice-lines.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(invoiceLinesRoute)],
  declarations: [InvoiceLinesComponent, InvoiceLinesDetailComponent, InvoiceLinesUpdateComponent, InvoiceLinesDeleteDialogComponent],
  entryComponents: [InvoiceLinesDeleteDialogComponent]
})
export class EpmwebInvoiceLinesModule {}
