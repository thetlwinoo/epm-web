import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISupplierTransactions } from 'app/shared/model/supplier-transactions.model';
import { SupplierTransactionsService } from './supplier-transactions.service';
import { SupplierTransactionsDeleteDialogComponent } from './supplier-transactions-delete-dialog.component';

@Component({
  selector: 'jhi-supplier-transactions',
  templateUrl: './supplier-transactions.component.html'
})
export class SupplierTransactionsComponent implements OnInit, OnDestroy {
  supplierTransactions: ISupplierTransactions[];
  eventSubscriber: Subscription;

  constructor(
    protected supplierTransactionsService: SupplierTransactionsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.supplierTransactionsService.query().subscribe((res: HttpResponse<ISupplierTransactions[]>) => {
      this.supplierTransactions = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSupplierTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISupplierTransactions) {
    return item.id;
  }

  registerChangeInSupplierTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('supplierTransactionsListModification', () => this.loadAll());
  }

  delete(supplierTransactions: ISupplierTransactions) {
    const modalRef = this.modalService.open(SupplierTransactionsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.supplierTransactions = supplierTransactions;
  }
}
