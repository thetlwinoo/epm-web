import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IStockItemHoldings, StockItemHoldings } from 'app/shared/model/stock-item-holdings.model';
import { StockItemHoldingsService } from './stock-item-holdings.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';

@Component({
  selector: 'jhi-stock-item-holdings-update',
  templateUrl: './stock-item-holdings-update.component.html'
})
export class StockItemHoldingsUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitemholdingonstockitems: IStockItems[];

  editForm = this.fb.group({
    id: [],
    quantityOnHand: [null, [Validators.required]],
    binLocation: [null, [Validators.required]],
    lastStocktakeQuantity: [null, [Validators.required]],
    lastCostPrice: [null, [Validators.required]],
    reorderLevel: [null, [Validators.required]],
    targerStockLevel: [null, [Validators.required]],
    stockItemHoldingOnStockItemId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stockItemHoldingsService: StockItemHoldingsService,
    protected stockItemsService: StockItemsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stockItemHoldings }) => {
      this.updateForm(stockItemHoldings);
    });
    this.stockItemsService.query({ 'stockItemHoldingId.specified': 'false' }).subscribe(
      (res: HttpResponse<IStockItems[]>) => {
        if (!this.editForm.get('stockItemHoldingOnStockItemId').value) {
          this.stockitemholdingonstockitems = res.body;
        } else {
          this.stockItemsService
            .find(this.editForm.get('stockItemHoldingOnStockItemId').value)
            .subscribe(
              (subRes: HttpResponse<IStockItems>) => (this.stockitemholdingonstockitems = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(stockItemHoldings: IStockItemHoldings) {
    this.editForm.patchValue({
      id: stockItemHoldings.id,
      quantityOnHand: stockItemHoldings.quantityOnHand,
      binLocation: stockItemHoldings.binLocation,
      lastStocktakeQuantity: stockItemHoldings.lastStocktakeQuantity,
      lastCostPrice: stockItemHoldings.lastCostPrice,
      reorderLevel: stockItemHoldings.reorderLevel,
      targerStockLevel: stockItemHoldings.targerStockLevel,
      stockItemHoldingOnStockItemId: stockItemHoldings.stockItemHoldingOnStockItemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stockItemHoldings = this.createFromForm();
    if (stockItemHoldings.id !== undefined) {
      this.subscribeToSaveResponse(this.stockItemHoldingsService.update(stockItemHoldings));
    } else {
      this.subscribeToSaveResponse(this.stockItemHoldingsService.create(stockItemHoldings));
    }
  }

  private createFromForm(): IStockItemHoldings {
    return {
      ...new StockItemHoldings(),
      id: this.editForm.get(['id']).value,
      quantityOnHand: this.editForm.get(['quantityOnHand']).value,
      binLocation: this.editForm.get(['binLocation']).value,
      lastStocktakeQuantity: this.editForm.get(['lastStocktakeQuantity']).value,
      lastCostPrice: this.editForm.get(['lastCostPrice']).value,
      reorderLevel: this.editForm.get(['reorderLevel']).value,
      targerStockLevel: this.editForm.get(['targerStockLevel']).value,
      stockItemHoldingOnStockItemId: this.editForm.get(['stockItemHoldingOnStockItemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItemHoldings>>) {
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
}
