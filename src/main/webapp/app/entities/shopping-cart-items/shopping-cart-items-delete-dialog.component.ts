import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';
import { ShoppingCartItemsService } from './shopping-cart-items.service';

@Component({
  templateUrl: './shopping-cart-items-delete-dialog.component.html'
})
export class ShoppingCartItemsDeleteDialogComponent {
  shoppingCartItems: IShoppingCartItems;

  constructor(
    protected shoppingCartItemsService: ShoppingCartItemsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.shoppingCartItemsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'shoppingCartItemsListModification',
        content: 'Deleted an shoppingCartItems'
      });
      this.activeModal.dismiss(true);
    });
  }
}
