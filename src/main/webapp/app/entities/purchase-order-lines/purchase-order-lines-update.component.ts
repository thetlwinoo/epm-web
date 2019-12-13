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
import { IPurchaseOrderLines, PurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';
import { PurchaseOrderLinesService } from './purchase-order-lines.service';
import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from 'app/entities/package-types/package-types.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from 'app/entities/purchase-orders/purchase-orders.service';

@Component({
  selector: 'jhi-purchase-order-lines-update',
  templateUrl: './purchase-order-lines-update.component.html'
})
export class PurchaseOrderLinesUpdateComponent implements OnInit {
  isSaving: boolean;

  packagetypes: IPackageTypes[];

  stockitems: IStockItems[];

  purchaseorders: IPurchaseOrders[];

  editForm = this.fb.group({
    id: [],
    orderedOuters: [null, [Validators.required]],
    description: [null, [Validators.required]],
    receivedOuters: [null, [Validators.required]],
    expectedUnitPricePerOuter: [],
    lastReceiptDate: [],
    isOrderLineFinalized: [null, [Validators.required]],
    lastEditedBy: [],
    lastEditedWhen: [],
    packageTypeId: [],
    stockItemId: [],
    purchaseOrderId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected purchaseOrderLinesService: PurchaseOrderLinesService,
    protected packageTypesService: PackageTypesService,
    protected stockItemsService: StockItemsService,
    protected purchaseOrdersService: PurchaseOrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ purchaseOrderLines }) => {
      this.updateForm(purchaseOrderLines);
    });
    this.packageTypesService
      .query()
      .subscribe(
        (res: HttpResponse<IPackageTypes[]>) => (this.packagetypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.stockItemsService
      .query()
      .subscribe((res: HttpResponse<IStockItems[]>) => (this.stockitems = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.purchaseOrdersService
      .query()
      .subscribe(
        (res: HttpResponse<IPurchaseOrders[]>) => (this.purchaseorders = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(purchaseOrderLines: IPurchaseOrderLines) {
    this.editForm.patchValue({
      id: purchaseOrderLines.id,
      orderedOuters: purchaseOrderLines.orderedOuters,
      description: purchaseOrderLines.description,
      receivedOuters: purchaseOrderLines.receivedOuters,
      expectedUnitPricePerOuter: purchaseOrderLines.expectedUnitPricePerOuter,
      lastReceiptDate: purchaseOrderLines.lastReceiptDate != null ? purchaseOrderLines.lastReceiptDate.format(DATE_TIME_FORMAT) : null,
      isOrderLineFinalized: purchaseOrderLines.isOrderLineFinalized,
      lastEditedBy: purchaseOrderLines.lastEditedBy,
      lastEditedWhen: purchaseOrderLines.lastEditedWhen != null ? purchaseOrderLines.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      packageTypeId: purchaseOrderLines.packageTypeId,
      stockItemId: purchaseOrderLines.stockItemId,
      purchaseOrderId: purchaseOrderLines.purchaseOrderId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const purchaseOrderLines = this.createFromForm();
    if (purchaseOrderLines.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseOrderLinesService.update(purchaseOrderLines));
    } else {
      this.subscribeToSaveResponse(this.purchaseOrderLinesService.create(purchaseOrderLines));
    }
  }

  private createFromForm(): IPurchaseOrderLines {
    return {
      ...new PurchaseOrderLines(),
      id: this.editForm.get(['id']).value,
      orderedOuters: this.editForm.get(['orderedOuters']).value,
      description: this.editForm.get(['description']).value,
      receivedOuters: this.editForm.get(['receivedOuters']).value,
      expectedUnitPricePerOuter: this.editForm.get(['expectedUnitPricePerOuter']).value,
      lastReceiptDate:
        this.editForm.get(['lastReceiptDate']).value != null
          ? moment(this.editForm.get(['lastReceiptDate']).value, DATE_TIME_FORMAT)
          : undefined,
      isOrderLineFinalized: this.editForm.get(['isOrderLineFinalized']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      packageTypeId: this.editForm.get(['packageTypeId']).value,
      stockItemId: this.editForm.get(['stockItemId']).value,
      purchaseOrderId: this.editForm.get(['purchaseOrderId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrderLines>>) {
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

  trackPackageTypesById(index: number, item: IPackageTypes) {
    return item.id;
  }

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }

  trackPurchaseOrdersById(index: number, item: IPurchaseOrders) {
    return item.id;
  }
}
