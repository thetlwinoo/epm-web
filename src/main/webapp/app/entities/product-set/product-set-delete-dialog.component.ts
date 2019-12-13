import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductSet } from 'app/shared/model/product-set.model';
import { ProductSetService } from './product-set.service';

@Component({
  templateUrl: './product-set-delete-dialog.component.html'
})
export class ProductSetDeleteDialogComponent {
  productSet: IProductSet;

  constructor(
    protected productSetService: ProductSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productSetService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productSetListModification',
        content: 'Deleted an productSet'
      });
      this.activeModal.dismiss(true);
    });
  }
}
