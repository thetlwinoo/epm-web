import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from './product-option-set.service';

@Component({
  templateUrl: './product-option-set-delete-dialog.component.html'
})
export class ProductOptionSetDeleteDialogComponent {
  productOptionSet: IProductOptionSet;

  constructor(
    protected productOptionSetService: ProductOptionSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productOptionSetService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productOptionSetListModification',
        content: 'Deleted an productOptionSet'
      });
      this.activeModal.dismiss(true);
    });
  }
}
