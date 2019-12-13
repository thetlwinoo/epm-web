import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from './transaction-types.service';

@Component({
  templateUrl: './transaction-types-delete-dialog.component.html'
})
export class TransactionTypesDeleteDialogComponent {
  transactionTypes: ITransactionTypes;

  constructor(
    protected transactionTypesService: TransactionTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionTypesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'transactionTypesListModification',
        content: 'Deleted an transactionTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}
