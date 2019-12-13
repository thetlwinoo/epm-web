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
import { IStockItemTransactions, StockItemTransactions } from 'app/shared/model/stock-item-transactions.model';
import { StockItemTransactionsService } from './stock-item-transactions.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from 'app/entities/invoices/invoices.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/suppliers.service';
import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from 'app/entities/transaction-types/transaction-types.service';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from 'app/entities/purchase-orders/purchase-orders.service';

@Component({
  selector: 'jhi-stock-item-transactions-update',
  templateUrl: './stock-item-transactions-update.component.html'
})
export class StockItemTransactionsUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitems: IStockItems[];

  customers: ICustomers[];

  invoices: IInvoices[];

  suppliers: ISuppliers[];

  transactiontypes: ITransactionTypes[];

  purchaseorders: IPurchaseOrders[];

  editForm = this.fb.group({
    id: [],
    transactionOccuredWhen: [null, [Validators.required]],
    quantity: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    stockItemId: [],
    customerId: [],
    invoiceId: [],
    supplierId: [],
    transactionTypeId: [],
    purchaseOrderId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stockItemTransactionsService: StockItemTransactionsService,
    protected stockItemsService: StockItemsService,
    protected customersService: CustomersService,
    protected invoicesService: InvoicesService,
    protected suppliersService: SuppliersService,
    protected transactionTypesService: TransactionTypesService,
    protected purchaseOrdersService: PurchaseOrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stockItemTransactions }) => {
      this.updateForm(stockItemTransactions);
    });
    this.stockItemsService
      .query()
      .subscribe((res: HttpResponse<IStockItems[]>) => (this.stockitems = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.customersService
      .query()
      .subscribe((res: HttpResponse<ICustomers[]>) => (this.customers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.invoicesService
      .query()
      .subscribe((res: HttpResponse<IInvoices[]>) => (this.invoices = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.suppliersService
      .query()
      .subscribe((res: HttpResponse<ISuppliers[]>) => (this.suppliers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.transactionTypesService
      .query()
      .subscribe(
        (res: HttpResponse<ITransactionTypes[]>) => (this.transactiontypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.purchaseOrdersService
      .query()
      .subscribe(
        (res: HttpResponse<IPurchaseOrders[]>) => (this.purchaseorders = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(stockItemTransactions: IStockItemTransactions) {
    this.editForm.patchValue({
      id: stockItemTransactions.id,
      transactionOccuredWhen:
        stockItemTransactions.transactionOccuredWhen != null ? stockItemTransactions.transactionOccuredWhen.format(DATE_TIME_FORMAT) : null,
      quantity: stockItemTransactions.quantity,
      lastEditedBy: stockItemTransactions.lastEditedBy,
      lastEditedWhen: stockItemTransactions.lastEditedWhen != null ? stockItemTransactions.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      stockItemId: stockItemTransactions.stockItemId,
      customerId: stockItemTransactions.customerId,
      invoiceId: stockItemTransactions.invoiceId,
      supplierId: stockItemTransactions.supplierId,
      transactionTypeId: stockItemTransactions.transactionTypeId,
      purchaseOrderId: stockItemTransactions.purchaseOrderId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stockItemTransactions = this.createFromForm();
    if (stockItemTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.stockItemTransactionsService.update(stockItemTransactions));
    } else {
      this.subscribeToSaveResponse(this.stockItemTransactionsService.create(stockItemTransactions));
    }
  }

  private createFromForm(): IStockItemTransactions {
    return {
      ...new StockItemTransactions(),
      id: this.editForm.get(['id']).value,
      transactionOccuredWhen:
        this.editForm.get(['transactionOccuredWhen']).value != null
          ? moment(this.editForm.get(['transactionOccuredWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      quantity: this.editForm.get(['quantity']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      stockItemId: this.editForm.get(['stockItemId']).value,
      customerId: this.editForm.get(['customerId']).value,
      invoiceId: this.editForm.get(['invoiceId']).value,
      supplierId: this.editForm.get(['supplierId']).value,
      transactionTypeId: this.editForm.get(['transactionTypeId']).value,
      purchaseOrderId: this.editForm.get(['purchaseOrderId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItemTransactions>>) {
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

  trackCustomersById(index: number, item: ICustomers) {
    return item.id;
  }

  trackInvoicesById(index: number, item: IInvoices) {
    return item.id;
  }

  trackSuppliersById(index: number, item: ISuppliers) {
    return item.id;
  }

  trackTransactionTypesById(index: number, item: ITransactionTypes) {
    return item.id;
  }

  trackPurchaseOrdersById(index: number, item: IPurchaseOrders) {
    return item.id;
  }
}
