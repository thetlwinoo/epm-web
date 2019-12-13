import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from './product-brand.service';

@Component({
  templateUrl: './product-brand-delete-dialog.component.html'
})
export class ProductBrandDeleteDialogComponent {
  productBrand: IProductBrand;

  constructor(
    protected productBrandService: ProductBrandService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productBrandService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productBrandListModification',
        content: 'Deleted an productBrand'
      });
      this.activeModal.dismiss(true);
    });
  }
}
