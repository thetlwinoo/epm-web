import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { SystemParametersComponent } from './system-parameters.component';
import { SystemParametersDetailComponent } from './system-parameters-detail.component';
import { SystemParametersUpdateComponent } from './system-parameters-update.component';
import { SystemParametersDeleteDialogComponent } from './system-parameters-delete-dialog.component';
import { systemParametersRoute } from './system-parameters.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(systemParametersRoute)],
  declarations: [
    SystemParametersComponent,
    SystemParametersDetailComponent,
    SystemParametersUpdateComponent,
    SystemParametersDeleteDialogComponent
  ],
  entryComponents: [SystemParametersDeleteDialogComponent]
})
export class EpmwebSystemParametersModule {}
