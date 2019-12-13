import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProductAttribute, ProductAttribute } from 'app/shared/model/product-attribute.model';
import { ProductAttributeService } from './product-attribute.service';
import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';
import { ProductAttributeSetService } from 'app/entities/product-attribute-set/product-attribute-set.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/suppliers.service';

@Component({
  selector: 'jhi-product-attribute-update',
  templateUrl: './product-attribute-update.component.html'
})
export class ProductAttributeUpdateComponent implements OnInit {
  isSaving: boolean;

  productattributesets: IProductAttributeSet[];

  suppliers: ISuppliers[];

  editForm = this.fb.group({
    id: [],
    value: [null, [Validators.required]],
    productAttributeSetId: [],
    supplierId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productAttributeService: ProductAttributeService,
    protected productAttributeSetService: ProductAttributeSetService,
    protected suppliersService: SuppliersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productAttribute }) => {
      this.updateForm(productAttribute);
    });
    this.productAttributeSetService
      .query()
      .subscribe(
        (res: HttpResponse<IProductAttributeSet[]>) => (this.productattributesets = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.suppliersService
      .query()
      .subscribe((res: HttpResponse<ISuppliers[]>) => (this.suppliers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productAttribute: IProductAttribute) {
    this.editForm.patchValue({
      id: productAttribute.id,
      value: productAttribute.value,
      productAttributeSetId: productAttribute.productAttributeSetId,
      supplierId: productAttribute.supplierId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productAttribute = this.createFromForm();
    if (productAttribute.id !== undefined) {
      this.subscribeToSaveResponse(this.productAttributeService.update(productAttribute));
    } else {
      this.subscribeToSaveResponse(this.productAttributeService.create(productAttribute));
    }
  }

  private createFromForm(): IProductAttribute {
    return {
      ...new ProductAttribute(),
      id: this.editForm.get(['id']).value,
      value: this.editForm.get(['value']).value,
      productAttributeSetId: this.editForm.get(['productAttributeSetId']).value,
      supplierId: this.editForm.get(['supplierId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductAttribute>>) {
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

  trackProductAttributeSetById(index: number, item: IProductAttributeSet) {
    return item.id;
  }

  trackSuppliersById(index: number, item: ISuppliers) {
    return item.id;
  }
}
