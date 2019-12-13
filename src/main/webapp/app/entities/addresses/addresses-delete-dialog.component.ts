import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAddresses } from 'app/shared/model/addresses.model';
import { AddressesService } from './addresses.service';

@Component({
  templateUrl: './addresses-delete-dialog.component.html'
})
export class AddressesDeleteDialogComponent {
  addresses: IAddresses;

  constructor(protected addressesService: AddressesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.addressesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'addressesListModification',
        content: 'Deleted an addresses'
      });
      this.activeModal.dismiss(true);
    });
  }
}
