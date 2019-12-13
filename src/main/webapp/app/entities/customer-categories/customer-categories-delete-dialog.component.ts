import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerCategories } from 'app/shared/model/customer-categories.model';
import { CustomerCategoriesService } from './customer-categories.service';

@Component({
  templateUrl: './customer-categories-delete-dialog.component.html'
})
export class CustomerCategoriesDeleteDialogComponent {
  customerCategories: ICustomerCategories;

  constructor(
    protected customerCategoriesService: CustomerCategoriesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customerCategoriesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'customerCategoriesListModification',
        content: 'Deleted an customerCategories'
      });
      this.activeModal.dismiss(true);
    });
  }
}
