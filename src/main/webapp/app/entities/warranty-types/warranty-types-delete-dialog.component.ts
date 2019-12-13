import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWarrantyTypes } from 'app/shared/model/warranty-types.model';
import { WarrantyTypesService } from './warranty-types.service';

@Component({
  templateUrl: './warranty-types-delete-dialog.component.html'
})
export class WarrantyTypesDeleteDialogComponent {
  warrantyTypes: IWarrantyTypes;

  constructor(
    protected warrantyTypesService: WarrantyTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.warrantyTypesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'warrantyTypesListModification',
        content: 'Deleted an warrantyTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}
