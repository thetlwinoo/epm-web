import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStateProvinces } from 'app/shared/model/state-provinces.model';
import { StateProvincesService } from './state-provinces.service';

@Component({
  templateUrl: './state-provinces-delete-dialog.component.html'
})
export class StateProvincesDeleteDialogComponent {
  stateProvinces: IStateProvinces;

  constructor(
    protected stateProvincesService: StateProvincesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stateProvincesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'stateProvincesListModification',
        content: 'Deleted an stateProvinces'
      });
      this.activeModal.dismiss(true);
    });
  }
}
