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
import { ICustomerTransactions, CustomerTransactions } from 'app/shared/model/customer-transactions.model';
import { CustomerTransactionsService } from './customer-transactions.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from 'app/entities/payment-transactions/payment-transactions.service';
import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from 'app/entities/transaction-types/transaction-types.service';
import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from 'app/entities/invoices/invoices.service';

@Component({
  selector: 'jhi-customer-transactions-update',
  templateUrl: './customer-transactions-update.component.html'
})
export class CustomerTransactionsUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomers[];

  paymenttransactions: IPaymentTransactions[];

  transactiontypes: ITransactionTypes[];

  invoices: IInvoices[];

  editForm = this.fb.group({
    id: [],
    transactionDate: [null, [Validators.required]],
    amountExcludingTax: [null, [Validators.required]],
    taxAmount: [null, [Validators.required]],
    transactionAmount: [null, [Validators.required]],
    outstandingBalance: [null, [Validators.required]],
    finalizationDate: [],
    isFinalized: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    customerId: [],
    paymentTransactionId: [],
    transactionTypeId: [],
    invoiceId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customerTransactionsService: CustomerTransactionsService,
    protected customersService: CustomersService,
    protected paymentTransactionsService: PaymentTransactionsService,
    protected transactionTypesService: TransactionTypesService,
    protected invoicesService: InvoicesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customerTransactions }) => {
      this.updateForm(customerTransactions);
    });
    this.customersService
      .query()
      .subscribe((res: HttpResponse<ICustomers[]>) => (this.customers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.paymentTransactionsService
      .query()
      .subscribe(
        (res: HttpResponse<IPaymentTransactions[]>) => (this.paymenttransactions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.transactionTypesService
      .query()
      .subscribe(
        (res: HttpResponse<ITransactionTypes[]>) => (this.transactiontypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.invoicesService
      .query()
      .subscribe((res: HttpResponse<IInvoices[]>) => (this.invoices = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(customerTransactions: ICustomerTransactions) {
    this.editForm.patchValue({
      id: customerTransactions.id,
      transactionDate: customerTransactions.transactionDate != null ? customerTransactions.transactionDate.format(DATE_TIME_FORMAT) : null,
      amountExcludingTax: customerTransactions.amountExcludingTax,
      taxAmount: customerTransactions.taxAmount,
      transactionAmount: customerTransactions.transactionAmount,
      outstandingBalance: customerTransactions.outstandingBalance,
      finalizationDate:
        customerTransactions.finalizationDate != null ? customerTransactions.finalizationDate.format(DATE_TIME_FORMAT) : null,
      isFinalized: customerTransactions.isFinalized,
      lastEditedBy: customerTransactions.lastEditedBy,
      lastEditedWhen: customerTransactions.lastEditedWhen != null ? customerTransactions.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      customerId: customerTransactions.customerId,
      paymentTransactionId: customerTransactions.paymentTransactionId,
      transactionTypeId: customerTransactions.transactionTypeId,
      invoiceId: customerTransactions.invoiceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customerTransactions = this.createFromForm();
    if (customerTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.customerTransactionsService.update(customerTransactions));
    } else {
      this.subscribeToSaveResponse(this.customerTransactionsService.create(customerTransactions));
    }
  }

  private createFromForm(): ICustomerTransactions {
    return {
      ...new CustomerTransactions(),
      id: this.editForm.get(['id']).value,
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
      customerId: this.editForm.get(['customerId']).value,
      paymentTransactionId: this.editForm.get(['paymentTransactionId']).value,
      transactionTypeId: this.editForm.get(['transactionTypeId']).value,
      invoiceId: this.editForm.get(['invoiceId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerTransactions>>) {
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

  trackCustomersById(index: number, item: ICustomers) {
    return item.id;
  }

  trackPaymentTransactionsById(index: number, item: IPaymentTransactions) {
    return item.id;
  }

  trackTransactionTypesById(index: number, item: ITransactionTypes) {
    return item.id;
  }

  trackInvoicesById(index: number, item: IInvoices) {
    return item.id;
  }
}
