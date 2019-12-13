import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from './stock-items.service';

@Component({
  templateUrl: './stock-items-delete-dialog.component.html'
})
export class StockItemsDeleteDialogComponent {
  stockItems: IStockItems;

  constructor(
    protected stockItemsService: StockItemsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockItemsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'stockItemsListModification',
        content: 'Deleted an stockItems'
      });
      this.activeModal.dismiss(true);
    });
  }
}
