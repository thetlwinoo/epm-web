import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProductOptionSet, ProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from './product-option-set.service';

@Component({
  selector: 'jhi-product-option-set-update',
  templateUrl: './product-option-set-update.component.html'
})
export class ProductOptionSetUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    value: [null, [Validators.required]]
  });

  constructor(
    protected productOptionSetService: ProductOptionSetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productOptionSet }) => {
      this.updateForm(productOptionSet);
    });
  }

  updateForm(productOptionSet: IProductOptionSet) {
    this.editForm.patchValue({
      id: productOptionSet.id,
      value: productOptionSet.value
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productOptionSet = this.createFromForm();
    if (productOptionSet.id !== undefined) {
      this.subscribeToSaveResponse(this.productOptionSetService.update(productOptionSet));
    } else {
      this.subscribeToSaveResponse(this.productOptionSetService.create(productOptionSet));
    }
  }

  private createFromForm(): IProductOptionSet {
    return {
      ...new ProductOptionSet(),
      id: this.editForm.get(['id']).value,
      value: this.editForm.get(['value']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductOptionSet>>) {
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
