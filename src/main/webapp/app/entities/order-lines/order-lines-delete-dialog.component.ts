import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderLines } from 'app/shared/model/order-lines.model';
import { OrderLinesService } from './order-lines.service';

@Component({
  templateUrl: './order-lines-delete-dialog.component.html'
})
export class OrderLinesDeleteDialogComponent {
  orderLines: IOrderLines;

  constructor(
    protected orderLinesService: OrderLinesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderLinesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'orderLinesListModification',
        content: 'Deleted an orderLines'
      });
      this.activeModal.dismiss(true);
    });
  }
}
