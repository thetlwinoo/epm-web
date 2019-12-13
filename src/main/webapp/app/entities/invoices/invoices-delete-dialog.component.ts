import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from './invoices.service';

@Component({
  templateUrl: './invoices-delete-dialog.component.html'
})
export class InvoicesDeleteDialogComponent {
  invoices: IInvoices;

  constructor(protected invoicesService: InvoicesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.invoicesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'invoicesListModification',
        content: 'Deleted an invoices'
      });
      this.activeModal.dismiss(true);
    });
  }
}
