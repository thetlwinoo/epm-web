import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { SpecialDealsComponent } from './special-deals.component';
import { SpecialDealsDetailComponent } from './special-deals-detail.component';
import { SpecialDealsUpdateComponent } from './special-deals-update.component';
import { SpecialDealsDeleteDialogComponent } from './special-deals-delete-dialog.component';
import { specialDealsRoute } from './special-deals.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(specialDealsRoute)],
  declarations: [SpecialDealsComponent, SpecialDealsDetailComponent, SpecialDealsUpdateComponent, SpecialDealsDeleteDialogComponent],
  entryComponents: [SpecialDealsDeleteDialogComponent]
})
export class EpmwebSpecialDealsModule {}
