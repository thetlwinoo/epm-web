import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from './delivery-methods.service';

@Component({
  templateUrl: './delivery-methods-delete-dialog.component.html'
})
export class DeliveryMethodsDeleteDialogComponent {
  deliveryMethods: IDeliveryMethods;

  constructor(
    protected deliveryMethodsService: DeliveryMethodsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.deliveryMethodsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'deliveryMethodsListModification',
        content: 'Deleted an deliveryMethods'
      });
      this.activeModal.dismiss(true);
    });
  }
}
