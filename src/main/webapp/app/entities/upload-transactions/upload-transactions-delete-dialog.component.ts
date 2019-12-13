import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from './upload-transactions.service';

@Component({
  templateUrl: './upload-transactions-delete-dialog.component.html'
})
export class UploadTransactionsDeleteDialogComponent {
  uploadTransactions: IUploadTransactions;

  constructor(
    protected uploadTransactionsService: UploadTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.uploadTransactionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'uploadTransactionsListModification',
        content: 'Deleted an uploadTransactions'
      });
      this.activeModal.dismiss(true);
    });
  }
}
