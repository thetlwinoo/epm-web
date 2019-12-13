import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from './special-deals.service';

@Component({
  templateUrl: './special-deals-delete-dialog.component.html'
})
export class SpecialDealsDeleteDialogComponent {
  specialDeals: ISpecialDeals;

  constructor(
    protected specialDealsService: SpecialDealsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.specialDealsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'specialDealsListModification',
        content: 'Deleted an specialDeals'
      });
      this.activeModal.dismiss(true);
    });
  }
}
