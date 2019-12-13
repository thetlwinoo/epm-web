import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from './purchase-orders.service';

@Component({
  templateUrl: './purchase-orders-delete-dialog.component.html'
})
export class PurchaseOrdersDeleteDialogComponent {
  purchaseOrders: IPurchaseOrders;

  constructor(
    protected purchaseOrdersService: PurchaseOrdersService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.purchaseOrdersService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'purchaseOrdersListModification',
        content: 'Deleted an purchaseOrders'
      });
      this.activeModal.dismiss(true);
    });
  }
}
