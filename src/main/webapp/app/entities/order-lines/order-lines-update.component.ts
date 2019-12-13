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
import { IOrderLines, OrderLines } from 'app/shared/model/order-lines.model';
import { OrderLinesService } from './order-lines.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from 'app/entities/package-types/package-types.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders/orders.service';

@Component({
  selector: 'jhi-order-lines-update',
  templateUrl: './order-lines-update.component.html'
})
export class OrderLinesUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitems: IStockItems[];

  packagetypes: IPackageTypes[];

  orders: IOrders[];

  editForm = this.fb.group({
    id: [],
    carrierTrackingNumber: [],
    quantity: [null, [Validators.required]],
    unitPrice: [],
    unitPriceDiscount: [],
    lineTotal: [],
    taxRate: [],
    pickedQuantity: [],
    pickingCompletedWhen: [],
    status: [null, [Validators.required]],
    lastEditedBy: [],
    lastEditedWhen: [],
    stockItemId: [],
    packageTypeId: [],
    orderId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected orderLinesService: OrderLinesService,
    protected stockItemsService: StockItemsService,
    protected packageTypesService: PackageTypesService,
    protected ordersService: OrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderLines }) => {
      this.updateForm(orderLines);
    });
    this.stockItemsService
      .query()
      .subscribe((res: HttpResponse<IStockItems[]>) => (this.stockitems = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.packageTypesService
      .query()
      .subscribe(
        (res: HttpResponse<IPackageTypes[]>) => (this.packagetypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.ordersService
      .query()
      .subscribe((res: HttpResponse<IOrders[]>) => (this.orders = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(orderLines: IOrderLines) {
    this.editForm.patchValue({
      id: orderLines.id,
      carrierTrackingNumber: orderLines.carrierTrackingNumber,
      quantity: orderLines.quantity,
      unitPrice: orderLines.unitPrice,
      unitPriceDiscount: orderLines.unitPriceDiscount,
      lineTotal: orderLines.lineTotal,
      taxRate: orderLines.taxRate,
      pickedQuantity: orderLines.pickedQuantity,
      pickingCompletedWhen: orderLines.pickingCompletedWhen != null ? orderLines.pickingCompletedWhen.format(DATE_TIME_FORMAT) : null,
      status: orderLines.status,
      lastEditedBy: orderLines.lastEditedBy,
      lastEditedWhen: orderLines.lastEditedWhen != null ? orderLines.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      stockItemId: orderLines.stockItemId,
      packageTypeId: orderLines.packageTypeId,
      orderId: orderLines.orderId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orderLines = this.createFromForm();
    if (orderLines.id !== undefined) {
      this.subscribeToSaveResponse(this.orderLinesService.update(orderLines));
    } else {
      this.subscribeToSaveResponse(this.orderLinesService.create(orderLines));
    }
  }

  private createFromForm(): IOrderLines {
    return {
      ...new OrderLines(),
      id: this.editForm.get(['id']).value,
      carrierTrackingNumber: this.editForm.get(['carrierTrackingNumber']).value,
      quantity: this.editForm.get(['quantity']).value,
      unitPrice: this.editForm.get(['unitPrice']).value,
      unitPriceDiscount: this.editForm.get(['unitPriceDiscount']).value,
      lineTotal: this.editForm.get(['lineTotal']).value,
      taxRate: this.editForm.get(['taxRate']).value,
      pickedQuantity: this.editForm.get(['pickedQuantity']).value,
      pickingCompletedWhen:
        this.editForm.get(['pickingCompletedWhen']).value != null
          ? moment(this.editForm.get(['pickingCompletedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      status: this.editForm.get(['status']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      stockItemId: this.editForm.get(['stockItemId']).value,
      packageTypeId: this.editForm.get(['packageTypeId']).value,
      orderId: this.editForm.get(['orderId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderLines>>) {
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

  trackPackageTypesById(index: number, item: IPackageTypes) {
    return item.id;
  }

  trackOrdersById(index: number, item: IOrders) {
    return item.id;
  }
}
