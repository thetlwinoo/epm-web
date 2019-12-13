import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWishlistProducts } from 'app/shared/model/wishlist-products.model';
import { WishlistProductsService } from './wishlist-products.service';

@Component({
  templateUrl: './wishlist-products-delete-dialog.component.html'
})
export class WishlistProductsDeleteDialogComponent {
  wishlistProducts: IWishlistProducts;

  constructor(
    protected wishlistProductsService: WishlistProductsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.wishlistProductsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'wishlistProductsListModification',
        content: 'Deleted an wishlistProducts'
      });
      this.activeModal.dismiss(true);
    });
  }
}
