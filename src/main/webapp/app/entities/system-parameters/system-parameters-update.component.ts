import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ISystemParameters, SystemParameters } from 'app/shared/model/system-parameters.model';
import { SystemParametersService } from './system-parameters.service';
import { ICities } from 'app/shared/model/cities.model';
import { CitiesService } from 'app/entities/cities/cities.service';

@Component({
  selector: 'jhi-system-parameters-update',
  templateUrl: './system-parameters-update.component.html'
})
export class SystemParametersUpdateComponent implements OnInit {
  isSaving: boolean;

  cities: ICities[];

  editForm = this.fb.group({
    id: [],
    applicationSettings: [null, [Validators.required]],
    deliveryCityId: [],
    postalCityId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected systemParametersService: SystemParametersService,
    protected citiesService: CitiesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ systemParameters }) => {
      this.updateForm(systemParameters);
    });
    this.citiesService
      .query()
      .subscribe((res: HttpResponse<ICities[]>) => (this.cities = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(systemParameters: ISystemParameters) {
    this.editForm.patchValue({
      id: systemParameters.id,
      applicationSettings: systemParameters.applicationSettings,
      deliveryCityId: systemParameters.deliveryCityId,
      postalCityId: systemParameters.postalCityId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const systemParameters = this.createFromForm();
    if (systemParameters.id !== undefined) {
      this.subscribeToSaveResponse(this.systemParametersService.update(systemParameters));
    } else {
      this.subscribeToSaveResponse(this.systemParametersService.create(systemParameters));
    }
  }

  private createFromForm(): ISystemParameters {
    return {
      ...new SystemParameters(),
      id: this.editForm.get(['id']).value,
      applicationSettings: this.editForm.get(['applicationSettings']).value,
      deliveryCityId: this.editForm.get(['deliveryCityId']).value,
      postalCityId: this.editForm.get(['postalCityId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystemParameters>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCitiesById(index: number, item: ICities) {
    return item.id;
  }
}
