import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerTransactions } from 'app/shared/model/customer-transactions.model';
import { CustomerTransactionsService } from './customer-transactions.service';

@Component({
  templateUrl: './customer-transactions-delete-dialog.component.html'
})
export class CustomerTransactionsDeleteDialogComponent {
  customerTransactions: ICustomerTransactions;

  constructor(
    protected customerTransactionsService: CustomerTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customerTransactionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'customerTransactionsListModification',
        content: 'Deleted an customerTransactions'
      });
      this.activeModal.dismiss(true);
    });
  }
}
