import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { DeliveryMethodsComponent } from './delivery-methods.component';
import { DeliveryMethodsDetailComponent } from './delivery-methods-detail.component';
import { DeliveryMethodsUpdateComponent } from './delivery-methods-update.component';
import { DeliveryMethodsDeleteDialogComponent } from './delivery-methods-delete-dialog.component';
import { deliveryMethodsRoute } from './delivery-methods.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(deliveryMethodsRoute)],
  declarations: [
    DeliveryMethodsComponent,
    DeliveryMethodsDetailComponent,
    DeliveryMethodsUpdateComponent,
    DeliveryMethodsDeleteDialogComponent
  ],
  entryComponents: [DeliveryMethodsDeleteDialogComponent]
})
export class EpmwebDeliveryMethodsModule {}
