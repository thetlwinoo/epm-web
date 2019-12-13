import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompareProducts } from 'app/shared/model/compare-products.model';
import { CompareProductsService } from './compare-products.service';

@Component({
  templateUrl: './compare-products-delete-dialog.component.html'
})
export class CompareProductsDeleteDialogComponent {
  compareProducts: ICompareProducts;

  constructor(
    protected compareProductsService: CompareProductsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.compareProductsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'compareProductsListModification',
        content: 'Deleted an compareProducts'
      });
      this.activeModal.dismiss(true);
    });
  }
}
