import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { OrderLinesComponent } from './order-lines.component';
import { OrderLinesDetailComponent } from './order-lines-detail.component';
import { OrderLinesUpdateComponent } from './order-lines-update.component';
import { OrderLinesDeleteDialogComponent } from './order-lines-delete-dialog.component';
import { orderLinesRoute } from './order-lines.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(orderLinesRoute)],
  declarations: [OrderLinesComponent, OrderLinesDetailComponent, OrderLinesUpdateComponent, OrderLinesDeleteDialogComponent],
  entryComponents: [OrderLinesDeleteDialogComponent]
})
export class EpmwebOrderLinesModule {}
