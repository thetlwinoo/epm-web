import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';
import { ProductAttributeSetService } from './product-attribute-set.service';

@Component({
  templateUrl: './product-attribute-set-delete-dialog.component.html'
})
export class ProductAttributeSetDeleteDialogComponent {
  productAttributeSet: IProductAttributeSet;

  constructor(
    protected productAttributeSetService: ProductAttributeSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productAttributeSetService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productAttributeSetListModification',
        content: 'Deleted an productAttributeSet'
      });
      this.activeModal.dismiss(true);
    });
  }
}
