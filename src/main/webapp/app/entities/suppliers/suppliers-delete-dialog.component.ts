import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from './suppliers.service';

@Component({
  templateUrl: './suppliers-delete-dialog.component.html'
})
export class SuppliersDeleteDialogComponent {
  suppliers: ISuppliers;

  constructor(protected suppliersService: SuppliersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.suppliersService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'suppliersListModification',
        content: 'Deleted an suppliers'
      });
      this.activeModal.dismiss(true);
    });
  }
}
