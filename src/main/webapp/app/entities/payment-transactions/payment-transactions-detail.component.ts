import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';

@Component({
  selector: 'jhi-payment-transactions-detail',
  templateUrl: './payment-transactions-detail.component.html'
})
export class PaymentTransactionsDetailComponent implements OnInit {
  paymentTransactions: IPaymentTransactions;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paymentTransactions }) => {
      this.paymentTransactions = paymentTransactions;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
