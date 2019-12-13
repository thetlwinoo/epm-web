import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ShipMethodComponent } from './ship-method.component';
import { ShipMethodDetailComponent } from './ship-method-detail.component';
import { ShipMethodUpdateComponent } from './ship-method-update.component';
import { ShipMethodDeleteDialogComponent } from './ship-method-delete-dialog.component';
import { shipMethodRoute } from './ship-method.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(shipMethodRoute)],
  declarations: [ShipMethodComponent, ShipMethodDetailComponent, ShipMethodUpdateComponent, ShipMethodDeleteDialogComponent],
  entryComponents: [ShipMethodDeleteDialogComponent]
})
export class EpmwebShipMethodModule {}
