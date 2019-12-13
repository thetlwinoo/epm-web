import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStockItemTransactions } from 'app/shared/model/stock-item-transactions.model';
import { StockItemTransactionsService } from './stock-item-transactions.service';
import { StockItemTransactionsDeleteDialogComponent } from './stock-item-transactions-delete-dialog.component';

@Component({
  selector: 'jhi-stock-item-transactions',
  templateUrl: './stock-item-transactions.component.html'
})
export class StockItemTransactionsComponent implements OnInit, OnDestroy {
  stockItemTransactions: IStockItemTransactions[];
  eventSubscriber: Subscription;

  constructor(
    protected stockItemTransactionsService: StockItemTransactionsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.stockItemTransactionsService.query().subscribe((res: HttpResponse<IStockItemTransactions[]>) => {
      this.stockItemTransactions = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInStockItemTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStockItemTransactions) {
    return item.id;
  }

  registerChangeInStockItemTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('stockItemTransactionsListModification', () => this.loadAll());
  }

  delete(stockItemTransactions: IStockItemTransactions) {
    const modalRef = this.modalService.open(StockItemTransactionsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.stockItemTransactions = stockItemTransactions;
  }
}
