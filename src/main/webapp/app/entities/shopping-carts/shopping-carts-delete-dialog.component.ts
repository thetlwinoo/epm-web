import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from './shopping-carts.service';

@Component({
  templateUrl: './shopping-carts-delete-dialog.component.html'
})
export class ShoppingCartsDeleteDialogComponent {
  shoppingCarts: IShoppingCarts;

  constructor(
    protected shoppingCartsService: ShoppingCartsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.shoppingCartsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'shoppingCartsListModification',
        content: 'Deleted an shoppingCarts'
      });
      this.activeModal.dismiss(true);
    });
  }
}
