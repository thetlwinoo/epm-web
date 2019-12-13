import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { StateProvincesComponent } from './state-provinces.component';
import { StateProvincesDetailComponent } from './state-provinces-detail.component';
import { StateProvincesUpdateComponent } from './state-provinces-update.component';
import { StateProvincesDeleteDialogComponent } from './state-provinces-delete-dialog.component';
import { stateProvincesRoute } from './state-provinces.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(stateProvincesRoute)],
  declarations: [
    StateProvincesComponent,
    StateProvincesDetailComponent,
    StateProvincesUpdateComponent,
    StateProvincesDeleteDialogComponent
  ],
  entryComponents: [StateProvincesDeleteDialogComponent]
})
export class EpmwebStateProvincesModule {}
