import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICompareProducts, CompareProducts } from 'app/shared/model/compare-products.model';
import { CompareProductsService } from './compare-products.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products/products.service';
import { ICompares } from 'app/shared/model/compares.model';
import { ComparesService } from 'app/entities/compares/compares.service';

@Component({
  selector: 'jhi-compare-products-update',
  templateUrl: './compare-products-update.component.html'
})
export class CompareProductsUpdateComponent implements OnInit {
  isSaving: boolean;

  products: IProducts[];

  compares: ICompares[];

  editForm = this.fb.group({
    id: [],
    productId: [],
    compareId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compareProductsService: CompareProductsService,
    protected productsService: ProductsService,
    protected comparesService: ComparesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compareProducts }) => {
      this.updateForm(compareProducts);
    });
    this.productsService
      .query()
      .subscribe((res: HttpResponse<IProducts[]>) => (this.products = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.comparesService
      .query()
      .subscribe((res: HttpResponse<ICompares[]>) => (this.compares = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(compareProducts: ICompareProducts) {
    this.editForm.patchValue({
      id: compareProducts.id,
      productId: compareProducts.productId,
      compareId: compareProducts.compareId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compareProducts = this.createFromForm();
    if (compareProducts.id !== undefined) {
      this.subscribeToSaveResponse(this.compareProductsService.update(compareProducts));
    } else {
      this.subscribeToSaveResponse(this.compareProductsService.create(compareProducts));
    }
  }

  private createFromForm(): ICompareProducts {
    return {
      ...new CompareProducts(),
      id: this.editForm.get(['id']).value,
      productId: this.editForm.get(['productId']).value,
      compareId: this.editForm.get(['compareId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompareProducts>>) {
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

  trackProductsById(index: number, item: IProducts) {
    return item.id;
  }

  trackComparesById(index: number, item: ICompares) {
    return item.id;
  }
}
