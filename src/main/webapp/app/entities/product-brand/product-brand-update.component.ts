import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProductBrand, ProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from './product-brand.service';

@Component({
  selector: 'jhi-product-brand-update',
  templateUrl: './product-brand-update.component.html'
})
export class ProductBrandUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    thumbnailUrl: []
  });

  constructor(protected productBrandService: ProductBrandService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productBrand }) => {
      this.updateForm(productBrand);
    });
  }

  updateForm(productBrand: IProductBrand) {
    this.editForm.patchValue({
      id: productBrand.id,
      name: productBrand.name,
      thumbnailUrl: productBrand.thumbnailUrl
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productBrand = this.createFromForm();
    if (productBrand.id !== undefined) {
      this.subscribeToSaveResponse(this.productBrandService.update(productBrand));
    } else {
      this.subscribeToSaveResponse(this.productBrandService.create(productBrand));
    }
  }

  private createFromForm(): IProductBrand {
    return {
      ...new ProductBrand(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      thumbnailUrl: this.editForm.get(['thumbnailUrl']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductBrand>>) {
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
