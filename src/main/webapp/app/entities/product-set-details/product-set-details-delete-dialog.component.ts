import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductSetDetails } from 'app/shared/model/product-set-details.model';
import { ProductSetDetailsService } from './product-set-details.service';

@Component({
  templateUrl: './product-set-details-delete-dialog.component.html'
})
export class ProductSetDetailsDeleteDialogComponent {
  productSetDetails: IProductSetDetails;

  constructor(
    protected productSetDetailsService: ProductSetDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productSetDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productSetDetailsListModification',
        content: 'Deleted an productSetDetails'
      });
      this.activeModal.dismiss(true);
    });
  }
}
