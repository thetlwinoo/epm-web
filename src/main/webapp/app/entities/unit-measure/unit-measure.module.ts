import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { UnitMeasureComponent } from './unit-measure.component';
import { UnitMeasureDetailComponent } from './unit-measure-detail.component';
import { UnitMeasureUpdateComponent } from './unit-measure-update.component';
import { UnitMeasureDeleteDialogComponent } from './unit-measure-delete-dialog.component';
import { unitMeasureRoute } from './unit-measure.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(unitMeasureRoute)],
  declarations: [UnitMeasureComponent, UnitMeasureDetailComponent, UnitMeasureUpdateComponent, UnitMeasureDeleteDialogComponent],
  entryComponents: [UnitMeasureDeleteDialogComponent]
})
export class EpmwebUnitMeasureModule {}
