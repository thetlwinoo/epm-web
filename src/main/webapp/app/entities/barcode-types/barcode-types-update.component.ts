import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBarcodeTypes, BarcodeTypes } from 'app/shared/model/barcode-types.model';
import { BarcodeTypesService } from './barcode-types.service';

@Component({
  selector: 'jhi-barcode-types-update',
  templateUrl: './barcode-types-update.component.html'
})
export class BarcodeTypesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(protected barcodeTypesService: BarcodeTypesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ barcodeTypes }) => {
      this.updateForm(barcodeTypes);
    });
  }

  updateForm(barcodeTypes: IBarcodeTypes) {
    this.editForm.patchValue({
      id: barcodeTypes.id,
      name: barcodeTypes.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const barcodeTypes = this.createFromForm();
    if (barcodeTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.barcodeTypesService.update(barcodeTypes));
    } else {
      this.subscribeToSaveResponse(this.barcodeTypesService.create(barcodeTypes));
    }
  }

  private createFromForm(): IBarcodeTypes {
    return {
      ...new BarcodeTypes(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBarcodeTypes>>) {
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
