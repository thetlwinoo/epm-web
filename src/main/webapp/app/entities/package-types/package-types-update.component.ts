import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPackageTypes, PackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from './package-types.service';

@Component({
  selector: 'jhi-package-types-update',
  templateUrl: './package-types-update.component.html'
})
export class PackageTypesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(protected packageTypesService: PackageTypesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ packageTypes }) => {
      this.updateForm(packageTypes);
    });
  }

  updateForm(packageTypes: IPackageTypes) {
    this.editForm.patchValue({
      id: packageTypes.id,
      name: packageTypes.name,
      validFrom: packageTypes.validFrom != null ? packageTypes.validFrom.format(DATE_TIME_FORMAT) : null,
      validTo: packageTypes.validTo != null ? packageTypes.validTo.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const packageTypes = this.createFromForm();
    if (packageTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.packageTypesService.update(packageTypes));
    } else {
      this.subscribeToSaveResponse(this.packageTypesService.create(packageTypes));
    }
  }

  private createFromForm(): IPackageTypes {
    return {
      ...new PackageTypes(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      validFrom:
        this.editForm.get(['validFrom']).value != null ? moment(this.editForm.get(['validFrom']).value, DATE_TIME_FORMAT) : undefined,
      validTo: this.editForm.get(['validTo']).value != null ? moment(this.editForm.get(['validTo']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPackageTypes>>) {
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
