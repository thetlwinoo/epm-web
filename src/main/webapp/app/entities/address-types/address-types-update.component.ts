import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAddressTypes, AddressTypes } from 'app/shared/model/address-types.model';
import { AddressTypesService } from './address-types.service';

@Component({
  selector: 'jhi-address-types-update',
  templateUrl: './address-types-update.component.html'
})
export class AddressTypesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    refer: []
  });

  constructor(protected addressTypesService: AddressTypesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ addressTypes }) => {
      this.updateForm(addressTypes);
    });
  }

  updateForm(addressTypes: IAddressTypes) {
    this.editForm.patchValue({
      id: addressTypes.id,
      name: addressTypes.name,
      refer: addressTypes.refer
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const addressTypes = this.createFromForm();
    if (addressTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.addressTypesService.update(addressTypes));
    } else {
      this.subscribeToSaveResponse(this.addressTypesService.create(addressTypes));
    }
  }

  private createFromForm(): IAddressTypes {
    return {
      ...new AddressTypes(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      refer: this.editForm.get(['refer']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddressTypes>>) {
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
