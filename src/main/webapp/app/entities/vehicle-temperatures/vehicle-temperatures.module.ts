import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { VehicleTemperaturesComponent } from './vehicle-temperatures.component';
import { VehicleTemperaturesDetailComponent } from './vehicle-temperatures-detail.component';
import { VehicleTemperaturesUpdateComponent } from './vehicle-temperatures-update.component';
import { VehicleTemperaturesDeleteDialogComponent } from './vehicle-temperatures-delete-dialog.component';
import { vehicleTemperaturesRoute } from './vehicle-temperatures.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(vehicleTemperaturesRoute)],
  declarations: [
    VehicleTemperaturesComponent,
    VehicleTemperaturesDetailComponent,
    VehicleTemperaturesUpdateComponent,
    VehicleTemperaturesDeleteDialogComponent
  ],
  entryComponents: [VehicleTemperaturesDeleteDialogComponent]
})
export class EpmwebVehicleTemperaturesModule {}
