import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMaterials, Materials } from 'app/shared/model/materials.model';
import { MaterialsService } from './materials.service';

@Component({
  selector: 'jhi-materials-update',
  templateUrl: './materials-update.component.html'
})
export class MaterialsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(protected materialsService: MaterialsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ materials }) => {
      this.updateForm(materials);
    });
  }

  updateForm(materials: IMaterials) {
    this.editForm.patchValue({
      id: materials.id,
      name: materials.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const materials = this.createFromForm();
    if (materials.id !== undefined) {
      this.subscribeToSaveResponse(this.materialsService.update(materials));
    } else {
      this.subscribeToSaveResponse(this.materialsService.create(materials));
    }
  }

  private createFromForm(): IMaterials {
    return {
      ...new Materials(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaterials>>) {
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
