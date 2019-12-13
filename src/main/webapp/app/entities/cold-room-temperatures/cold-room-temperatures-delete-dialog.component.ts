import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';
import { ColdRoomTemperaturesService } from './cold-room-temperatures.service';

@Component({
  templateUrl: './cold-room-temperatures-delete-dialog.component.html'
})
export class ColdRoomTemperaturesDeleteDialogComponent {
  coldRoomTemperatures: IColdRoomTemperatures;

  constructor(
    protected coldRoomTemperaturesService: ColdRoomTemperaturesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.coldRoomTemperaturesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'coldRoomTemperaturesListModification',
        content: 'Deleted an coldRoomTemperatures'
      });
      this.activeModal.dismiss(true);
    });
  }
}
