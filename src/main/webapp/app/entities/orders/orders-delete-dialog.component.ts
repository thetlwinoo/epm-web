import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';

@Component({
  templateUrl: './orders-delete-dialog.component.html'
})
export class OrdersDeleteDialogComponent {
  orders: IOrders;

  constructor(protected ordersService: OrdersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ordersService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'ordersListModification',
        content: 'Deleted an orders'
      });
      this.activeModal.dismiss(true);
    });
  }
}
