import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProductChoice, ProductChoice } from 'app/shared/model/product-choice.model';
import { ProductChoiceService } from './product-choice.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';
import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';
import { ProductAttributeSetService } from 'app/entities/product-attribute-set/product-attribute-set.service';
import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from 'app/entities/product-option-set/product-option-set.service';

@Component({
  selector: 'jhi-product-choice-update',
  templateUrl: './product-choice-update.component.html'
})
export class ProductChoiceUpdateComponent implements OnInit {
  isSaving: boolean;

  productcategories: IProductCategory[];

  productattributesets: IProductAttributeSet[];

  productoptionsets: IProductOptionSet[];

  editForm = this.fb.group({
    id: [],
    isMultiply: [null, [Validators.required]],
    productCategoryId: [],
    productAttributeSetId: [],
    productOptionSetId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productChoiceService: ProductChoiceService,
    protected productCategoryService: ProductCategoryService,
    protected productAttributeSetService: ProductAttributeSetService,
    protected productOptionSetService: ProductOptionSetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productChoice }) => {
      this.updateForm(productChoice);
    });
    this.productCategoryService
      .query()
      .subscribe(
        (res: HttpResponse<IProductCategory[]>) => (this.productcategories = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.productAttributeSetService
      .query()
      .subscribe(
        (res: HttpResponse<IProductAttributeSet[]>) => (this.productattributesets = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.productOptionSetService
      .query()
      .subscribe(
        (res: HttpResponse<IProductOptionSet[]>) => (this.productoptionsets = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(productChoice: IProductChoice) {
    this.editForm.patchValue({
      id: productChoice.id,
      isMultiply: productChoice.isMultiply,
      productCategoryId: productChoice.productCategoryId,
      productAttributeSetId: productChoice.productAttributeSetId,
      productOptionSetId: productChoice.productOptionSetId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productChoice = this.createFromForm();
    if (productChoice.id !== undefined) {
      this.subscribeToSaveResponse(this.productChoiceService.update(productChoice));
    } else {
      this.subscribeToSaveResponse(this.productChoiceService.create(productChoice));
    }
  }

  private createFromForm(): IProductChoice {
    return {
      ...new ProductChoice(),
      id: this.editForm.get(['id']).value,
      isMultiply: this.editForm.get(['isMultiply']).value,
      productCategoryId: this.editForm.get(['productCategoryId']).value,
      productAttributeSetId: this.editForm.get(['productAttributeSetId']).value,
      productOptionSetId: this.editForm.get(['productOptionSetId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductChoice>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProductCategoryById(index: number, item: IProductCategory) {
    return item.id;
  }

  trackProductAttributeSetById(index: number, item: IProductAttributeSet) {
    return item.id;
  }

  trackProductOptionSetById(index: number, item: IProductOptionSet) {
    return item.id;
  }
}
