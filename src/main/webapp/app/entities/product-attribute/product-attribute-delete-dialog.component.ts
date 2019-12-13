import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAttribute } from 'app/shared/model/product-attribute.model';
import { ProductAttributeService } from './product-attribute.service';

@Component({
  templateUrl: './product-attribute-delete-dialog.component.html'
})
export class ProductAttributeDeleteDialogComponent {
  productAttribute: IProductAttribute;

  constructor(
    protected productAttributeService: ProductAttributeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productAttributeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productAttributeListModification',
        content: 'Deleted an productAttribute'
      });
      this.activeModal.dismiss(true);
    });
  }
}
