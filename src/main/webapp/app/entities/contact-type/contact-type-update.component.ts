import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IContactType, ContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from './contact-type.service';

@Component({
  selector: 'jhi-contact-type-update',
  templateUrl: './contact-type-update.component.html'
})
export class ContactTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(protected contactTypeService: ContactTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contactType }) => {
      this.updateForm(contactType);
    });
  }

  updateForm(contactType: IContactType) {
    this.editForm.patchValue({
      id: contactType.id,
      name: contactType.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contactType = this.createFromForm();
    if (contactType.id !== undefined) {
      this.subscribeToSaveResponse(this.contactTypeService.update(contactType));
    } else {
      this.subscribeToSaveResponse(this.contactTypeService.create(contactType));
    }
  }

  private createFromForm(): IContactType {
    return {
      ...new ContactType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactType>>) {
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
