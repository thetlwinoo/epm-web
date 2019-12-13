import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from './payment-transactions.service';

@Component({
  templateUrl: './payment-transactions-delete-dialog.component.html'
})
export class PaymentTransactionsDeleteDialogComponent {
  paymentTransactions: IPaymentTransactions;

  constructor(
    protected paymentTransactionsService: PaymentTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.paymentTransactionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'paymentTransactionsListModification',
        content: 'Deleted an paymentTransactions'
      });
      this.activeModal.dismiss(true);
    });
  }
}
