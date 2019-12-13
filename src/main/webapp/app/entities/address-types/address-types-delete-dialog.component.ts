import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AddressTypesService } from './address-types.service';

@Component({
  templateUrl: './address-types-delete-dialog.component.html'
})
export class AddressTypesDeleteDialogComponent {
  addressTypes: IAddressTypes;

  constructor(
    protected addressTypesService: AddressTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.addressTypesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'addressTypesListModification',
        content: 'Deleted an addressTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}
