import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';
import { SupplierImportedDocumentService } from './supplier-imported-document.service';

@Component({
  templateUrl: './supplier-imported-document-delete-dialog.component.html'
})
export class SupplierImportedDocumentDeleteDialogComponent {
  supplierImportedDocument: ISupplierImportedDocument;

  constructor(
    protected supplierImportedDocumentService: SupplierImportedDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.supplierImportedDocumentService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'supplierImportedDocumentListModification',
        content: 'Deleted an supplierImportedDocument'
      });
      this.activeModal.dismiss(true);
    });
  }
}
