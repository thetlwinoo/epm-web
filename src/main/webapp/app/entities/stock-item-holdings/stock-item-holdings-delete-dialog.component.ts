import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';
import { StockItemHoldingsService } from './stock-item-holdings.service';

@Component({
  templateUrl: './stock-item-holdings-delete-dialog.component.html'
})
export class StockItemHoldingsDeleteDialogComponent {
  stockItemHoldings: IStockItemHoldings;

  constructor(
    protected stockItemHoldingsService: StockItemHoldingsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockItemHoldingsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'stockItemHoldingsListModification',
        content: 'Deleted an stockItemHoldings'
      });
      this.activeModal.dismiss(true);
    });
  }
}
