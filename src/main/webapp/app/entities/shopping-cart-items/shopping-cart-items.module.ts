import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ShoppingCartItemsComponent } from './shopping-cart-items.component';
import { ShoppingCartItemsDetailComponent } from './shopping-cart-items-detail.component';
import { ShoppingCartItemsUpdateComponent } from './shopping-cart-items-update.component';
import { ShoppingCartItemsDeleteDialogComponent } from './shopping-cart-items-delete-dialog.component';
import { shoppingCartItemsRoute } from './shopping-cart-items.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(shoppingCartItemsRoute)],
  declarations: [
    ShoppingCartItemsComponent,
    ShoppingCartItemsDetailComponent,
    ShoppingCartItemsUpdateComponent,
    ShoppingCartItemsDeleteDialogComponent
  ],
  entryComponents: [ShoppingCartItemsDeleteDialogComponent]
})
export class EpmwebShoppingCartItemsModule {}
