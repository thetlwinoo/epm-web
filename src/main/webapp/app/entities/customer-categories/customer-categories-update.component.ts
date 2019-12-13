import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICustomerCategories, CustomerCategories } from 'app/shared/model/customer-categories.model';
import { CustomerCategoriesService } from './customer-categories.service';

@Component({
  selector: 'jhi-customer-categories-update',
  templateUrl: './customer-categories-update.component.html'
})
export class CustomerCategoriesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(
    protected customerCategoriesService: CustomerCategoriesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customerCategories }) => {
      this.updateForm(customerCategories);
    });
  }

  updateForm(customerCategories: ICustomerCategories) {
    this.editForm.patchValue({
      id: customerCategories.id,
      name: customerCategories.name,
      validFrom: customerCategories.validFrom != null ? customerCategories.validFrom.format(DATE_TIME_FORMAT) : null,
      validTo: customerCategories.validTo != null ? customerCategories.validTo.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customerCategories = this.createFromForm();
    if (customerCategories.id !== undefined) {
      this.subscribeToSaveResponse(this.customerCategoriesService.update(customerCategories));
    } else {
      this.subscribeToSaveResponse(this.customerCategoriesService.create(customerCategories));
    }
  }

  private createFromForm(): ICustomerCategories {
    return {
      ...new CustomerCategories(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      validFrom:
        this.editForm.get(['validFrom']).value != null ? moment(this.editForm.get(['validFrom']).value, DATE_TIME_FORMAT) : undefined,
      validTo: this.editForm.get(['validTo']).value != null ? moment(this.editForm.get(['validTo']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerCategories>>) {
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
