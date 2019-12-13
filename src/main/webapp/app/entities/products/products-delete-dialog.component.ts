import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from './products.service';

@Component({
  templateUrl: './products-delete-dialog.component.html'
})
export class ProductsDeleteDialogComponent {
  products: IProducts;

  constructor(protected productsService: ProductsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productsListModification',
        content: 'Deleted an products'
      });
      this.activeModal.dismiss(true);
    });
  }
}
