import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProductCatalog, ProductCatalog } from 'app/shared/model/product-catalog.model';
import { ProductCatalogService } from './product-catalog.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products/products.service';

@Component({
  selector: 'jhi-product-catalog-update',
  templateUrl: './product-catalog-update.component.html'
})
export class ProductCatalogUpdateComponent implements OnInit {
  isSaving: boolean;

  productcategories: IProductCategory[];

  products: IProducts[];

  editForm = this.fb.group({
    id: [],
    productCategoryId: [],
    productId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productCatalogService: ProductCatalogService,
    protected productCategoryService: ProductCategoryService,
    protected productsService: ProductsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productCatalog }) => {
      this.updateForm(productCatalog);
    });
    this.productCategoryService
      .query()
      .subscribe(
        (res: HttpResponse<IProductCategory[]>) => (this.productcategories = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.productsService
      .query()
      .subscribe((res: HttpResponse<IProducts[]>) => (this.products = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productCatalog: IProductCatalog) {
    this.editForm.patchValue({
      id: productCatalog.id,
      productCategoryId: productCatalog.productCategoryId,
      productId: productCatalog.productId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productCatalog = this.createFromForm();
    if (productCatalog.id !== undefined) {
      this.subscribeToSaveResponse(this.productCatalogService.update(productCatalog));
    } else {
      this.subscribeToSaveResponse(this.productCatalogService.create(productCatalog));
    }
  }

  private createFromForm(): IProductCatalog {
    return {
      ...new ProductCatalog(),
      id: this.editForm.get(['id']).value,
      productCategoryId: this.editForm.get(['productCategoryId']).value,
      productId: this.editForm.get(['productId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductCatalog>>) {
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

  trackProductsById(index: number, item: IProducts) {
    return item.id;
  }
}
