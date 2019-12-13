import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductOption } from 'app/shared/model/product-option.model';
import { ProductOptionService } from './product-option.service';

@Component({
  templateUrl: './product-option-delete-dialog.component.html'
})
export class ProductOptionDeleteDialogComponent {
  productOption: IProductOption;

  constructor(
    protected productOptionService: ProductOptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productOptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productOptionListModification',
        content: 'Deleted an productOption'
      });
      this.activeModal.dismiss(true);
    });
  }
}
