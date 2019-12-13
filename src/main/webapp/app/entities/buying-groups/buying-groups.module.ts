import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { BuyingGroupsComponent } from './buying-groups.component';
import { BuyingGroupsDetailComponent } from './buying-groups-detail.component';
import { BuyingGroupsUpdateComponent } from './buying-groups-update.component';
import { BuyingGroupsDeleteDialogComponent } from './buying-groups-delete-dialog.component';
import { buyingGroupsRoute } from './buying-groups.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(buyingGroupsRoute)],
  declarations: [BuyingGroupsComponent, BuyingGroupsDetailComponent, BuyingGroupsUpdateComponent, BuyingGroupsDeleteDialogComponent],
  entryComponents: [BuyingGroupsDeleteDialogComponent]
})
export class EpmwebBuyingGroupsModule {}
