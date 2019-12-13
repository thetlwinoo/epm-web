import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBarcodeTypes } from 'app/shared/model/barcode-types.model';
import { BarcodeTypesService } from './barcode-types.service';

@Component({
  templateUrl: './barcode-types-delete-dialog.component.html'
})
export class BarcodeTypesDeleteDialogComponent {
  barcodeTypes: IBarcodeTypes;

  constructor(
    protected barcodeTypesService: BarcodeTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.barcodeTypesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'barcodeTypesListModification',
        content: 'Deleted an barcodeTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}
