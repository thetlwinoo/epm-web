import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';
import { VehicleTemperaturesService } from './vehicle-temperatures.service';

@Component({
  templateUrl: './vehicle-temperatures-delete-dialog.component.html'
})
export class VehicleTemperaturesDeleteDialogComponent {
  vehicleTemperatures: IVehicleTemperatures;

  constructor(
    protected vehicleTemperaturesService: VehicleTemperaturesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vehicleTemperaturesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'vehicleTemperaturesListModification',
        content: 'Deleted an vehicleTemperatures'
      });
      this.activeModal.dismiss(true);
    });
  }
}
