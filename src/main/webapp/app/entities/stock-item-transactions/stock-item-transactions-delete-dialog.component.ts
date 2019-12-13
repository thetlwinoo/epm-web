import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItemTransactions } from 'app/shared/model/stock-item-transactions.model';
import { StockItemTransactionsService } from './stock-item-transactions.service';

@Component({
  templateUrl: './stock-item-transactions-delete-dialog.component.html'
})
export class StockItemTransactionsDeleteDialogComponent {
  stockItemTransactions: IStockItemTransactions;

  constructor(
    protected stockItemTransactionsService: StockItemTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockItemTransactionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'stockItemTransactionsListModification',
        content: 'Deleted an stockItemTransactions'
      });
      this.activeModal.dismiss(true);
    });
  }
}
