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
import { IInvoiceLines, InvoiceLines } from 'app/shared/model/invoice-lines.model';
import { InvoiceLinesService } from './invoice-lines.service';
import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from 'app/entities/package-types/package-types.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from 'app/entities/invoices/invoices.service';

@Component({
  selector: 'jhi-invoice-lines-update',
  templateUrl: './invoice-lines-update.component.html'
})
export class InvoiceLinesUpdateComponent implements OnInit {
  isSaving: boolean;

  packagetypes: IPackageTypes[];

  stockitems: IStockItems[];

  invoices: IInvoices[];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    quantity: [null, [Validators.required]],
    unitPrice: [],
    taxRate: [null, [Validators.required]],
    taxAmount: [null, [Validators.required]],
    lineProfit: [null, [Validators.required]],
    extendedPrice: [null, [Validators.required]],
    lastEditedBy: [],
    lastEditedWhen: [],
    packageTypeId: [],
    stockItemId: [],
    invoiceId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected invoiceLinesService: InvoiceLinesService,
    protected packageTypesService: PackageTypesService,
    protected stockItemsService: StockItemsService,
    protected invoicesService: InvoicesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invoiceLines }) => {
      this.updateForm(invoiceLines);
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
    this.invoicesService
      .query()
      .subscribe((res: HttpResponse<IInvoices[]>) => (this.invoices = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(invoiceLines: IInvoiceLines) {
    this.editForm.patchValue({
      id: invoiceLines.id,
      description: invoiceLines.description,
      quantity: invoiceLines.quantity,
      unitPrice: invoiceLines.unitPrice,
      taxRate: invoiceLines.taxRate,
      taxAmount: invoiceLines.taxAmount,
      lineProfit: invoiceLines.lineProfit,
      extendedPrice: invoiceLines.extendedPrice,
      lastEditedBy: invoiceLines.lastEditedBy,
      lastEditedWhen: invoiceLines.lastEditedWhen != null ? invoiceLines.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      packageTypeId: invoiceLines.packageTypeId,
      stockItemId: invoiceLines.stockItemId,
      invoiceId: invoiceLines.invoiceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const invoiceLines = this.createFromForm();
    if (invoiceLines.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceLinesService.update(invoiceLines));
    } else {
      this.subscribeToSaveResponse(this.invoiceLinesService.create(invoiceLines));
    }
  }

  private createFromForm(): IInvoiceLines {
    return {
      ...new InvoiceLines(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      quantity: this.editForm.get(['quantity']).value,
      unitPrice: this.editForm.get(['unitPrice']).value,
      taxRate: this.editForm.get(['taxRate']).value,
      taxAmount: this.editForm.get(['taxAmount']).value,
      lineProfit: this.editForm.get(['lineProfit']).value,
      extendedPrice: this.editForm.get(['extendedPrice']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      packageTypeId: this.editForm.get(['packageTypeId']).value,
      stockItemId: this.editForm.get(['stockItemId']).value,
      invoiceId: this.editForm.get(['invoiceId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceLines>>) {
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

  trackInvoicesById(index: number, item: IInvoices) {
    return item.id;
  }
}
