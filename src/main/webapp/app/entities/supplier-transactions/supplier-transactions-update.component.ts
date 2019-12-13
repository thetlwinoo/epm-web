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
import { ISupplierTransactions, SupplierTransactions } from 'app/shared/model/supplier-transactions.model';
import { SupplierTransactionsService } from './supplier-transactions.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/suppliers.service';
import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from 'app/entities/transaction-types/transaction-types.service';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from 'app/entities/purchase-orders/purchase-orders.service';

@Component({
  selector: 'jhi-supplier-transactions-update',
  templateUrl: './supplier-transactions-update.component.html'
})
export class SupplierTransactionsUpdateComponent implements OnInit {
  isSaving: boolean;

  suppliers: ISuppliers[];

  transactiontypes: ITransactionTypes[];

  purchaseorders: IPurchaseOrders[];

  editForm = this.fb.group({
    id: [],
    supplierInvoiceNumber: [],
    transactionDate: [null, [Validators.required]],
    amountExcludingTax: [null, [Validators.required]],
    taxAmount: [null, [Validators.required]],
    transactionAmount: [null, [Validators.required]],
    outstandingBalance: [null, [Validators.required]],
    finalizationDate: [],
    isFinalized: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    supplierId: [],
    transactionTypeId: [],
    purchaseOrderId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected supplierTransactionsService: SupplierTransactionsService,
    protected suppliersService: SuppliersService,
    protected transactionTypesService: TransactionTypesService,
    protected purchaseOrdersService: PurchaseOrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ supplierTransactions }) => {
      this.updateForm(supplierTransactions);
    });
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

  updateForm(supplierTransactions: ISupplierTransactions) {
    this.editForm.patchValue({
      id: supplierTransactions.id,
      supplierInvoiceNumber: supplierTransactions.supplierInvoiceNumber,
      transactionDate: supplierTransactions.transactionDate != null ? supplierTransactions.transactionDate.format(DATE_TIME_FORMAT) : null,
      amountExcludingTax: supplierTransactions.amountExcludingTax,
      taxAmount: supplierTransactions.taxAmount,
      transactionAmount: supplierTransactions.transactionAmount,
      outstandingBalance: supplierTransactions.outstandingBalance,
      finalizationDate:
        supplierTransactions.finalizationDate != null ? supplierTransactions.finalizationDate.format(DATE_TIME_FORMAT) : null,
      isFinalized: supplierTransactions.isFinalized,
      lastEditedBy: supplierTransactions.lastEditedBy,
      lastEditedWhen: supplierTransactions.lastEditedWhen != null ? supplierTransactions.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      supplierId: supplierTransactions.supplierId,
      transactionTypeId: supplierTransactions.transactionTypeId,
      purchaseOrderId: supplierTransactions.purchaseOrderId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const supplierTransactions = this.createFromForm();
    if (supplierTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierTransactionsService.update(supplierTransactions));
    } else {
      this.subscribeToSaveResponse(this.supplierTransactionsService.create(supplierTransactions));
    }
  }

  private createFromForm(): ISupplierTransactions {
    return {
      ...new SupplierTransactions(),
      id: this.editForm.get(['id']).value,
      supplierInvoiceNumber: this.editForm.get(['supplierInvoiceNumber']).value,
      transactionDate:
        this.editForm.get(['transactionDate']).value != null
          ? moment(this.editForm.get(['transactionDate']).value, DATE_TIME_FORMAT)
          : undefined,
      amountExcludingTax: this.editForm.get(['amountExcludingTax']).value,
      taxAmount: this.editForm.get(['taxAmount']).value,
      transactionAmount: this.editForm.get(['transactionAmount']).value,
      outstandingBalance: this.editForm.get(['outstandingBalance']).value,
      finalizationDate:
        this.editForm.get(['finalizationDate']).value != null
          ? moment(this.editForm.get(['finalizationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      isFinalized: this.editForm.get(['isFinalized']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      supplierId: this.editForm.get(['supplierId']).value,
      transactionTypeId: this.editForm.get(['transactionTypeId']).value,
      purchaseOrderId: this.editForm.get(['purchaseOrderId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierTransactions>>) {
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
