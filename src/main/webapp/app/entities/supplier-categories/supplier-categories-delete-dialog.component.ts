import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';
import { SupplierCategoriesService } from './supplier-categories.service';

@Component({
  templateUrl: './supplier-categories-delete-dialog.component.html'
})
export class SupplierCategoriesDeleteDialogComponent {
  supplierCategories: ISupplierCategories;

  constructor(
    protected supplierCategoriesService: SupplierCategoriesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.supplierCategoriesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'supplierCategoriesListModification',
        content: 'Deleted an supplierCategories'
      });
      this.activeModal.dismiss(true);
    });
  }
}
