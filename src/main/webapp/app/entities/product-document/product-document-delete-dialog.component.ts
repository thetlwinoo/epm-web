import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductDocument } from 'app/shared/model/product-document.model';
import { ProductDocumentService } from './product-document.service';

@Component({
  templateUrl: './product-document-delete-dialog.component.html'
})
export class ProductDocumentDeleteDialogComponent {
  productDocument: IProductDocument;

  constructor(
    protected productDocumentService: ProductDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productDocumentService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'productDocumentListModification',
        content: 'Deleted an productDocument'
      });
      this.activeModal.dismiss(true);
    });
  }
}
