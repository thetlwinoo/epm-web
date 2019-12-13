import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystemParameters } from 'app/shared/model/system-parameters.model';
import { SystemParametersService } from './system-parameters.service';

@Component({
  templateUrl: './system-parameters-delete-dialog.component.html'
})
export class SystemParametersDeleteDialogComponent {
  systemParameters: ISystemParameters;

  constructor(
    protected systemParametersService: SystemParametersService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.systemParametersService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'systemParametersListModification',
        content: 'Deleted an systemParameters'
      });
      this.activeModal.dismiss(true);
    });
  }
}
