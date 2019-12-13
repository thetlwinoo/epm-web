import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShipMethod } from 'app/shared/model/ship-method.model';
import { ShipMethodService } from './ship-method.service';

@Component({
  templateUrl: './ship-method-delete-dialog.component.html'
})
export class ShipMethodDeleteDialogComponent {
  shipMethod: IShipMethod;

  constructor(
    protected shipMethodService: ShipMethodService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.shipMethodService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'shipMethodListModification',
        content: 'Deleted an shipMethod'
      });
      this.activeModal.dismiss(true);
    });
  }
}
