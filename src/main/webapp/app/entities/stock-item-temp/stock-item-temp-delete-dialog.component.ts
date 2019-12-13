import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItemTemp } from 'app/shared/model/stock-item-temp.model';
import { StockItemTempService } from './stock-item-temp.service';

@Component({
  templateUrl: './stock-item-temp-delete-dialog.component.html'
})
export class StockItemTempDeleteDialogComponent {
  stockItemTemp: IStockItemTemp;

  constructor(
    protected stockItemTempService: StockItemTempService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockItemTempService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'stockItemTempListModification',
        content: 'Deleted an stockItemTemp'
      });
      this.activeModal.dismiss(true);
    });
  }
}
