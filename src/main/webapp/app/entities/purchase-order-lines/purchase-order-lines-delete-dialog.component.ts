import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';
import { PurchaseOrderLinesService } from './purchase-order-lines.service';

@Component({
  templateUrl: './purchase-order-lines-delete-dialog.component.html'
})
export class PurchaseOrderLinesDeleteDialogComponent {
  purchaseOrderLines: IPurchaseOrderLines;

  constructor(
    protected purchaseOrderLinesService: PurchaseOrderLinesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.purchaseOrderLinesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'purchaseOrderLinesListModification',
        content: 'Deleted an purchaseOrderLines'
      });
      this.activeModal.dismiss(true);
    });
  }
}
