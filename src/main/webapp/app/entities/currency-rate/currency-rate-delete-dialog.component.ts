import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICurrencyRate } from 'app/shared/model/currency-rate.model';
import { CurrencyRateService } from './currency-rate.service';

@Component({
  templateUrl: './currency-rate-delete-dialog.component.html'
})
export class CurrencyRateDeleteDialogComponent {
  currencyRate: ICurrencyRate;

  constructor(
    protected currencyRateService: CurrencyRateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.currencyRateService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'currencyRateListModification',
        content: 'Deleted an currencyRate'
      });
      this.activeModal.dismiss(true);
    });
  }
}
