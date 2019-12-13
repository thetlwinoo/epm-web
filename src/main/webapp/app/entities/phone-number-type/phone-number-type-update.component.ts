import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPhoneNumberType, PhoneNumberType } from 'app/shared/model/phone-number-type.model';
import { PhoneNumberTypeService } from './phone-number-type.service';

@Component({
  selector: 'jhi-phone-number-type-update',
  templateUrl: './phone-number-type-update.component.html'
})
export class PhoneNumberTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(
    protected phoneNumberTypeService: PhoneNumberTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ phoneNumberType }) => {
      this.updateForm(phoneNumberType);
    });
  }

  updateForm(phoneNumberType: IPhoneNumberType) {
    this.editForm.patchValue({
      id: phoneNumberType.id,
      name: phoneNumberType.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const phoneNumberType = this.createFromForm();
    if (phoneNumberType.id !== undefined) {
      this.subscribeToSaveResponse(this.phoneNumberTypeService.update(phoneNumberType));
    } else {
      this.subscribeToSaveResponse(this.phoneNumberTypeService.create(phoneNumberType));
    }
  }

  private createFromForm(): IPhoneNumberType {
    return {
      ...new PhoneNumberType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhoneNumberType>>) {
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
