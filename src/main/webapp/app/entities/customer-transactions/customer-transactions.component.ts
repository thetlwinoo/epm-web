import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerTransactions } from 'app/shared/model/customer-transactions.model';
import { CustomerTransactionsService } from './customer-transactions.service';
import { CustomerTransactionsDeleteDialogComponent } from './customer-transactions-delete-dialog.component';

@Component({
  selector: 'jhi-customer-transactions',
  templateUrl: './customer-transactions.component.html'
})
export class CustomerTransactionsComponent implements OnInit, OnDestroy {
  customerTransactions: ICustomerTransactions[];
  eventSubscriber: Subscription;

  constructor(
    protected customerTransactionsService: CustomerTransactionsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.customerTransactionsService.query().subscribe((res: HttpResponse<ICustomerTransactions[]>) => {
      this.customerTransactions = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCustomerTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomerTransactions) {
    return item.id;
  }

  registerChangeInCustomerTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('customerTransactionsListModification', () => this.loadAll());
  }

  delete(customerTransactions: ICustomerTransactions) {
    const modalRef = this.modalService.open(CustomerTransactionsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customerTransactions = customerTransactions;
  }
}
