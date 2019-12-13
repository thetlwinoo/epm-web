import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProductAttributeSet, ProductAttributeSet } from 'app/shared/model/product-attribute-set.model';
import { ProductAttributeSetService } from './product-attribute-set.service';
import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from 'app/entities/product-option-set/product-option-set.service';

@Component({
  selector: 'jhi-product-attribute-set-update',
  templateUrl: './product-attribute-set-update.component.html'
})
export class ProductAttributeSetUpdateComponent implements OnInit {
  isSaving: boolean;

  productoptionsets: IProductOptionSet[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    productOptionSetId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productAttributeSetService: ProductAttributeSetService,
    protected productOptionSetService: ProductOptionSetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productAttributeSet }) => {
      this.updateForm(productAttributeSet);
    });
    this.productOptionSetService
      .query()
      .subscribe(
        (res: HttpResponse<IProductOptionSet[]>) => (this.productoptionsets = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(productAttributeSet: IProductAttributeSet) {
    this.editForm.patchValue({
      id: productAttributeSet.id,
      name: productAttributeSet.name,
      productOptionSetId: productAttributeSet.productOptionSetId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productAttributeSet = this.createFromForm();
    if (productAttributeSet.id !== undefined) {
      this.subscribeToSaveResponse(this.productAttributeSetService.update(productAttributeSet));
    } else {
      this.subscribeToSaveResponse(this.productAttributeSetService.create(productAttributeSet));
    }
  }

  private createFromForm(): IProductAttributeSet {
    return {
      ...new ProductAttributeSet(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      productOptionSetId: this.editForm.get(['productOptionSetId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductAttributeSet>>) {
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

  trackProductOptionSetById(index: number, item: IProductOptionSet) {
    return item.id;
  }
}
