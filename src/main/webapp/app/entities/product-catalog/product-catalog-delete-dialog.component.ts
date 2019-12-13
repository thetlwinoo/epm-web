import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductCatalog } from 'app/shared/model/product-catalog.model';
import { ProductCatalogService } from './product-catalog.service';

@Component({
  templateUrl: './product-catalog-delete-dialog.component.html'
})
export class ProductCatalogDeleteDialogComponent {
  productCatalog: IProductCatalog;

  constructor(
    protected productCatalogService: ProductCatalogService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productCatalogService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productCatalogListModification',
        content: 'Deleted an productCatalog'
      });
      this.activeModal.dismiss(true);
    });
  }
}
