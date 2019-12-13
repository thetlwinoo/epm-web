import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierTransactions } from 'app/shared/model/supplier-transactions.model';
import { SupplierTransactionsService } from './supplier-transactions.service';

@Component({
  templateUrl: './supplier-transactions-delete-dialog.component.html'
})
export class SupplierTransactionsDeleteDialogComponent {
  supplierTransactions: ISupplierTransactions;

  constructor(
    protected supplierTransactionsService: SupplierTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.supplierTransactionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'supplierTransactionsListModification',
        content: 'Deleted an supplierTransactions'
      });
      this.activeModal.dismiss(true);
    });
  }
}
