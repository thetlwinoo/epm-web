import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProductSetDetails, ProductSetDetails } from 'app/shared/model/product-set-details.model';
import { ProductSetDetailsService } from './product-set-details.service';

@Component({
  selector: 'jhi-product-set-details-update',
  templateUrl: './product-set-details-update.component.html'
})
export class ProductSetDetailsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    subGroupNo: [],
    subGroupMinCount: [],
    subGroupMinTotal: [null, [Validators.required]],
    minCount: [],
    maxCount: [],
    isOptional: []
  });

  constructor(
    protected productSetDetailsService: ProductSetDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productSetDetails }) => {
      this.updateForm(productSetDetails);
    });
  }

  updateForm(productSetDetails: IProductSetDetails) {
    this.editForm.patchValue({
      id: productSetDetails.id,
      subGroupNo: productSetDetails.subGroupNo,
      subGroupMinCount: productSetDetails.subGroupMinCount,
      subGroupMinTotal: productSetDetails.subGroupMinTotal,
      minCount: productSetDetails.minCount,
      maxCount: productSetDetails.maxCount,
      isOptional: productSetDetails.isOptional
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productSetDetails = this.createFromForm();
    if (productSetDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.productSetDetailsService.update(productSetDetails));
    } else {
      this.subscribeToSaveResponse(this.productSetDetailsService.create(productSetDetails));
    }
  }

  private createFromForm(): IProductSetDetails {
    return {
      ...new ProductSetDetails(),
      id: this.editForm.get(['id']).value,
      subGroupNo: this.editForm.get(['subGroupNo']).value,
      subGroupMinCount: this.editForm.get(['subGroupMinCount']).value,
      subGroupMinTotal: this.editForm.get(['subGroupMinTotal']).value,
      minCount: this.editForm.get(['minCount']).value,
      maxCount: this.editForm.get(['maxCount']).value,
      isOptional: this.editForm.get(['isOptional']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSetDetails>>) {
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
