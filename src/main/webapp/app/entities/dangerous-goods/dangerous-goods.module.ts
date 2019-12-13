import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { DangerousGoodsComponent } from './dangerous-goods.component';
import { DangerousGoodsDetailComponent } from './dangerous-goods-detail.component';
import { DangerousGoodsUpdateComponent } from './dangerous-goods-update.component';
import { DangerousGoodsDeleteDialogComponent } from './dangerous-goods-delete-dialog.component';
import { dangerousGoodsRoute } from './dangerous-goods.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(dangerousGoodsRoute)],
  declarations: [
    DangerousGoodsComponent,
    DangerousGoodsDetailComponent,
    DangerousGoodsUpdateComponent,
    DangerousGoodsDeleteDialogComponent
  ],
  entryComponents: [DangerousGoodsDeleteDialogComponent]
})
export class EpmwebDangerousGoodsModule {}
