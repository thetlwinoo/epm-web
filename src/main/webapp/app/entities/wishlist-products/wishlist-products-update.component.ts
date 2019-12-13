import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IWishlistProducts, WishlistProducts } from 'app/shared/model/wishlist-products.model';
import { WishlistProductsService } from './wishlist-products.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products/products.service';
import { IWishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from 'app/entities/wishlists/wishlists.service';

@Component({
  selector: 'jhi-wishlist-products-update',
  templateUrl: './wishlist-products-update.component.html'
})
export class WishlistProductsUpdateComponent implements OnInit {
  isSaving: boolean;

  products: IProducts[];

  wishlists: IWishlists[];

  editForm = this.fb.group({
    id: [],
    productId: [],
    wishlistId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected wishlistProductsService: WishlistProductsService,
    protected productsService: ProductsService,
    protected wishlistsService: WishlistsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ wishlistProducts }) => {
      this.updateForm(wishlistProducts);
    });
    this.productsService
      .query()
      .subscribe((res: HttpResponse<IProducts[]>) => (this.products = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.wishlistsService
      .query()
      .subscribe((res: HttpResponse<IWishlists[]>) => (this.wishlists = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(wishlistProducts: IWishlistProducts) {
    this.editForm.patchValue({
      id: wishlistProducts.id,
      productId: wishlistProducts.productId,
      wishlistId: wishlistProducts.wishlistId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const wishlistProducts = this.createFromForm();
    if (wishlistProducts.id !== undefined) {
      this.subscribeToSaveResponse(this.wishlistProductsService.update(wishlistProducts));
    } else {
      this.subscribeToSaveResponse(this.wishlistProductsService.create(wishlistProducts));
    }
  }

  private createFromForm(): IWishlistProducts {
    return {
      ...new WishlistProducts(),
      id: this.editForm.get(['id']).value,
      productId: this.editForm.get(['productId']).value,
      wishlistId: this.editForm.get(['wishlistId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWishlistProducts>>) {
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

  trackWishlistsById(index: number, item: IWishlists) {
    return item.id;
  }
}
