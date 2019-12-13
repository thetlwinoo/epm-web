import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IShoppingCartItems, ShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';
import { ShoppingCartItemsService } from './shopping-cart-items.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from 'app/entities/shopping-carts/shopping-carts.service';

@Component({
  selector: 'jhi-shopping-cart-items-update',
  templateUrl: './shopping-cart-items-update.component.html'
})
export class ShoppingCartItemsUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitems: IStockItems[];

  shoppingcarts: IShoppingCarts[];

  editForm = this.fb.group({
    id: [],
    quantity: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    stockItemId: [],
    cartId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected shoppingCartItemsService: ShoppingCartItemsService,
    protected stockItemsService: StockItemsService,
    protected shoppingCartsService: ShoppingCartsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ shoppingCartItems }) => {
      this.updateForm(shoppingCartItems);
    });
    this.stockItemsService
      .query()
      .subscribe((res: HttpResponse<IStockItems[]>) => (this.stockitems = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.shoppingCartsService
      .query()
      .subscribe(
        (res: HttpResponse<IShoppingCarts[]>) => (this.shoppingcarts = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(shoppingCartItems: IShoppingCartItems) {
    this.editForm.patchValue({
      id: shoppingCartItems.id,
      quantity: shoppingCartItems.quantity,
      lastEditedBy: shoppingCartItems.lastEditedBy,
      lastEditedWhen: shoppingCartItems.lastEditedWhen != null ? shoppingCartItems.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      stockItemId: shoppingCartItems.stockItemId,
      cartId: shoppingCartItems.cartId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const shoppingCartItems = this.createFromForm();
    if (shoppingCartItems.id !== undefined) {
      this.subscribeToSaveResponse(this.shoppingCartItemsService.update(shoppingCartItems));
    } else {
      this.subscribeToSaveResponse(this.shoppingCartItemsService.create(shoppingCartItems));
    }
  }

  private createFromForm(): IShoppingCartItems {
    return {
      ...new ShoppingCartItems(),
      id: this.editForm.get(['id']).value,
      quantity: this.editForm.get(['quantity']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      stockItemId: this.editForm.get(['stockItemId']).value,
      cartId: this.editForm.get(['cartId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShoppingCartItems>>) {
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

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }

  trackShoppingCartsById(index: number, item: IShoppingCarts) {
    return item.id;
  }
}
