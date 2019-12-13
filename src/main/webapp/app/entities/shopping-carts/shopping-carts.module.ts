import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ShoppingCartsComponent } from './shopping-carts.component';
import { ShoppingCartsDetailComponent } from './shopping-carts-detail.component';
import { ShoppingCartsUpdateComponent } from './shopping-carts-update.component';
import { ShoppingCartsDeleteDialogComponent } from './shopping-carts-delete-dialog.component';
import { shoppingCartsRoute } from './shopping-carts.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(shoppingCartsRoute)],
  declarations: [ShoppingCartsComponent, ShoppingCartsDetailComponent, ShoppingCartsUpdateComponent, ShoppingCartsDeleteDialogComponent],
  entryComponents: [ShoppingCartsDeleteDialogComponent]
})
export class EpmwebShoppingCartsModule {}
