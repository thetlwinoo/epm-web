import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IWarrantyTypes, WarrantyTypes } from 'app/shared/model/warranty-types.model';
import { WarrantyTypesService } from './warranty-types.service';

@Component({
  selector: 'jhi-warranty-types-update',
  templateUrl: './warranty-types-update.component.html'
})
export class WarrantyTypesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(protected warrantyTypesService: WarrantyTypesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ warrantyTypes }) => {
      this.updateForm(warrantyTypes);
    });
  }

  updateForm(warrantyTypes: IWarrantyTypes) {
    this.editForm.patchValue({
      id: warrantyTypes.id,
      name: warrantyTypes.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const warrantyTypes = this.createFromForm();
    if (warrantyTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.warrantyTypesService.update(warrantyTypes));
    } else {
      this.subscribeToSaveResponse(this.warrantyTypesService.create(warrantyTypes));
    }
  }

  private createFromForm(): IWarrantyTypes {
    return {
      ...new WarrantyTypes(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWarrantyTypes>>) {
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
