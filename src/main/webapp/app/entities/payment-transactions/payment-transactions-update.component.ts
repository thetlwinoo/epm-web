import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPaymentTransactions, PaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from './payment-transactions.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders/orders.service';

@Component({
  selector: 'jhi-payment-transactions-update',
  templateUrl: './payment-transactions-update.component.html'
})
export class PaymentTransactionsUpdateComponent implements OnInit {
  isSaving: boolean;

  paymentonorders: IOrders[];

  editForm = this.fb.group({
    id: [],
    returnedCompletedPaymentData: [null, [Validators.required]],
    lastEditedBy: [],
    lastEditedWhen: [],
    paymentOnOrderId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected paymentTransactionsService: PaymentTransactionsService,
    protected ordersService: OrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paymentTransactions }) => {
      this.updateForm(paymentTransactions);
    });
    this.ordersService.query({ 'paymentTransactionId.specified': 'false' }).subscribe(
      (res: HttpResponse<IOrders[]>) => {
        if (!this.editForm.get('paymentOnOrderId').value) {
          this.paymentonorders = res.body;
        } else {
          this.ordersService
            .find(this.editForm.get('paymentOnOrderId').value)
            .subscribe(
              (subRes: HttpResponse<IOrders>) => (this.paymentonorders = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(paymentTransactions: IPaymentTransactions) {
    this.editForm.patchValue({
      id: paymentTransactions.id,
      returnedCompletedPaymentData: paymentTransactions.returnedCompletedPaymentData,
      lastEditedBy: paymentTransactions.lastEditedBy,
      lastEditedWhen: paymentTransactions.lastEditedWhen != null ? paymentTransactions.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      paymentOnOrderId: paymentTransactions.paymentOnOrderId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paymentTransactions = this.createFromForm();
    if (paymentTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentTransactionsService.update(paymentTransactions));
    } else {
      this.subscribeToSaveResponse(this.paymentTransactionsService.create(paymentTransactions));
    }
  }

  private createFromForm(): IPaymentTransactions {
    return {
      ...new PaymentTransactions(),
      id: this.editForm.get(['id']).value,
      returnedCompletedPaymentData: this.editForm.get(['returnedCompletedPaymentData']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      paymentOnOrderId: this.editForm.get(['paymentOnOrderId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentTransactions>>) {
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

  trackOrdersById(index: number, item: IOrders) {
    return item.id;
  }
}
