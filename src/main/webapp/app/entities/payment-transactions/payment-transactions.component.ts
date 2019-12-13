import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from './payment-transactions.service';
import { PaymentTransactionsDeleteDialogComponent } from './payment-transactions-delete-dialog.component';

@Component({
  selector: 'jhi-payment-transactions',
  templateUrl: './payment-transactions.component.html'
})
export class PaymentTransactionsComponent implements OnInit, OnDestroy {
  paymentTransactions: IPaymentTransactions[];
  eventSubscriber: Subscription;

  constructor(
    protected paymentTransactionsService: PaymentTransactionsService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.paymentTransactionsService.query().subscribe((res: HttpResponse<IPaymentTransactions[]>) => {
      this.paymentTransactions = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPaymentTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaymentTransactions) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPaymentTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('paymentTransactionsListModification', () => this.loadAll());
  }

  delete(paymentTransactions: IPaymentTransactions) {
    const modalRef = this.modalService.open(PaymentTransactionsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentTransactions = paymentTransactions;
  }
}
