import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from './currency.service';

@Component({
  templateUrl: './currency-delete-dialog.component.html'
})
export class CurrencyDeleteDialogComponent {
  currency: ICurrency;

  constructor(protected currencyService: CurrencyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.currencyService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'currencyListModification',
        content: 'Deleted an currency'
      });
      this.activeModal.dismiss(true);
    });
  }
}
