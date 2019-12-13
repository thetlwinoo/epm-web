import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';

@Component({
  selector: 'jhi-vehicle-temperatures-detail',
  templateUrl: './vehicle-temperatures-detail.component.html'
})
export class VehicleTemperaturesDetailComponent implements OnInit {
  vehicleTemperatures: IVehicleTemperatures;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vehicleTemperatures }) => {
      this.vehicleTemperatures = vehicleTemperatures;
    });
  }

  previousState() {
    window.history.back();
  }
}
