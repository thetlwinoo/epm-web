import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { WishlistProductsComponent } from './wishlist-products.component';
import { WishlistProductsDetailComponent } from './wishlist-products-detail.component';
import { WishlistProductsUpdateComponent } from './wishlist-products-update.component';
import { WishlistProductsDeleteDialogComponent } from './wishlist-products-delete-dialog.component';
import { wishlistProductsRoute } from './wishlist-products.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(wishlistProductsRoute)],
  declarations: [
    WishlistProductsComponent,
    WishlistProductsDetailComponent,
    WishlistProductsUpdateComponent,
    WishlistProductsDeleteDialogComponent
  ],
  entryComponents: [WishlistProductsDeleteDialogComponent]
})
export class EpmwebWishlistProductsModule {}
