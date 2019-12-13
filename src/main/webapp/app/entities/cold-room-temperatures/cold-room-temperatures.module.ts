import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ColdRoomTemperaturesComponent } from './cold-room-temperatures.component';
import { ColdRoomTemperaturesDetailComponent } from './cold-room-temperatures-detail.component';
import { ColdRoomTemperaturesUpdateComponent } from './cold-room-temperatures-update.component';
import { ColdRoomTemperaturesDeleteDialogComponent } from './cold-room-temperatures-delete-dialog.component';
import { coldRoomTemperaturesRoute } from './cold-room-temperatures.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(coldRoomTemperaturesRoute)],
  declarations: [
    ColdRoomTemperaturesComponent,
    ColdRoomTemperaturesDetailComponent,
    ColdRoomTemperaturesUpdateComponent,
    ColdRoomTemperaturesDeleteDialogComponent
  ],
  entryComponents: [ColdRoomTemperaturesDeleteDialogComponent]
})
export class EpmwebColdRoomTemperaturesModule {}
