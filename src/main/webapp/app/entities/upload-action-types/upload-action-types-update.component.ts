import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUploadActionTypes, UploadActionTypes } from 'app/shared/model/upload-action-types.model';
import { UploadActionTypesService } from './upload-action-types.service';

@Component({
  selector: 'jhi-upload-action-types-update',
  templateUrl: './upload-action-types-update.component.html'
})
export class UploadActionTypesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(
    protected uploadActionTypesService: UploadActionTypesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ uploadActionTypes }) => {
      this.updateForm(uploadActionTypes);
    });
  }

  updateForm(uploadActionTypes: IUploadActionTypes) {
    this.editForm.patchValue({
      id: uploadActionTypes.id,
      name: uploadActionTypes.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const uploadActionTypes = this.createFromForm();
    if (uploadActionTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.uploadActionTypesService.update(uploadActionTypes));
    } else {
      this.subscribeToSaveResponse(this.uploadActionTypesService.create(uploadActionTypes));
    }
  }

  private createFromForm(): IUploadActionTypes {
    return {
      ...new UploadActionTypes(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUploadActionTypes>>) {
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
