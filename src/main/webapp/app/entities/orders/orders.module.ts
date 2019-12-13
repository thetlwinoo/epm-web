import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { OrdersComponent } from './orders.component';
import { OrdersDetailComponent } from './orders-detail.component';
import { OrdersUpdateComponent } from './orders-update.component';
import { OrdersDeleteDialogComponent } from './orders-delete-dialog.component';
import { ordersRoute } from './orders.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(ordersRoute)],
  declarations: [OrdersComponent, OrdersDetailComponent, OrdersUpdateComponent, OrdersDeleteDialogComponent],
  entryComponents: [OrdersDeleteDialogComponent]
})
export class EpmwebOrdersModule {}
