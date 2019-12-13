import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IVehicleTemperatures, VehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';
import { VehicleTemperaturesService } from './vehicle-temperatures.service';

@Component({
  selector: 'jhi-vehicle-temperatures-update',
  templateUrl: './vehicle-temperatures-update.component.html'
})
export class VehicleTemperaturesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    vehicleRegistration: [null, [Validators.required]],
    chillerSensorNumber: [null, [Validators.required]],
    recordedWhen: [null, [Validators.required]],
    temperature: [null, [Validators.required]],
    isCompressed: [null, [Validators.required]],
    fullSensorData: [],
    compressedSensorData: []
  });

  constructor(
    protected vehicleTemperaturesService: VehicleTemperaturesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vehicleTemperatures }) => {
      this.updateForm(vehicleTemperatures);
    });
  }

  updateForm(vehicleTemperatures: IVehicleTemperatures) {
    this.editForm.patchValue({
      id: vehicleTemperatures.id,
      vehicleRegistration: vehicleTemperatures.vehicleRegistration,
      chillerSensorNumber: vehicleTemperatures.chillerSensorNumber,
      recordedWhen: vehicleTemperatures.recordedWhen,
      temperature: vehicleTemperatures.temperature,
      isCompressed: vehicleTemperatures.isCompressed,
      fullSensorData: vehicleTemperatures.fullSensorData,
      compressedSensorData: vehicleTemperatures.compressedSensorData
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vehicleTemperatures = this.createFromForm();
    if (vehicleTemperatures.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleTemperaturesService.update(vehicleTemperatures));
    } else {
      this.subscribeToSaveResponse(this.vehicleTemperaturesService.create(vehicleTemperatures));
    }
  }

  private createFromForm(): IVehicleTemperatures {
    return {
      ...new VehicleTemperatures(),
      id: this.editForm.get(['id']).value,
      vehicleRegistration: this.editForm.get(['vehicleRegistration']).value,
      chillerSensorNumber: this.editForm.get(['chillerSensorNumber']).value,
      recordedWhen: this.editForm.get(['recordedWhen']).value,
      temperature: this.editForm.get(['temperature']).value,
      isCompressed: this.editForm.get(['isCompressed']).value,
      fullSensorData: this.editForm.get(['fullSensorData']).value,
      compressedSensorData: this.editForm.get(['compressedSensorData']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleTemperatures>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
