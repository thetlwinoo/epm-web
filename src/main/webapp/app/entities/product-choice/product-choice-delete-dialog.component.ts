import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductChoice } from 'app/shared/model/product-choice.model';
import { ProductChoiceService } from './product-choice.service';

@Component({
  templateUrl: './product-choice-delete-dialog.component.html'
})
export class ProductChoiceDeleteDialogComponent {
  productChoice: IProductChoice;

  constructor(
    protected productChoiceService: ProductChoiceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productChoiceService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productChoiceListModification',
        content: 'Deleted an productChoice'
      });
      this.activeModal.dismiss(true);
    });
  }
}
