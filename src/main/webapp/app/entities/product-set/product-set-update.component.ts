import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProductSet, ProductSet } from 'app/shared/model/product-set.model';
import { ProductSetService } from './product-set.service';

@Component({
  selector: 'jhi-product-set-update',
  templateUrl: './product-set-update.component.html'
})
export class ProductSetUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    noOfPerson: [null, [Validators.required]],
    isExclusive: []
  });

  constructor(protected productSetService: ProductSetService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productSet }) => {
      this.updateForm(productSet);
    });
  }

  updateForm(productSet: IProductSet) {
    this.editForm.patchValue({
      id: productSet.id,
      name: productSet.name,
      noOfPerson: productSet.noOfPerson,
      isExclusive: productSet.isExclusive
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productSet = this.createFromForm();
    if (productSet.id !== undefined) {
      this.subscribeToSaveResponse(this.productSetService.update(productSet));
    } else {
      this.subscribeToSaveResponse(this.productSetService.create(productSet));
    }
  }

  private createFromForm(): IProductSet {
    return {
      ...new ProductSet(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      noOfPerson: this.editForm.get(['noOfPerson']).value,
      isExclusive: this.editForm.get(['isExclusive']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSet>>) {
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
