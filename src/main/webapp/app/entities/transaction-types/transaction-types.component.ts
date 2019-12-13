import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from './transaction-types.service';
import { TransactionTypesDeleteDialogComponent } from './transaction-types-delete-dialog.component';

@Component({
  selector: 'jhi-transaction-types',
  templateUrl: './transaction-types.component.html'
})
export class TransactionTypesComponent implements OnInit, OnDestroy {
  transactionTypes: ITransactionTypes[];
  eventSubscriber: Subscription;

  constructor(
    protected transactionTypesService: TransactionTypesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.transactionTypesService.query().subscribe((res: HttpResponse<ITransactionTypes[]>) => {
      this.transactionTypes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTransactionTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransactionTypes) {
    return item.id;
  }

  registerChangeInTransactionTypes() {
    this.eventSubscriber = this.eventManager.subscribe('transactionTypesListModification', () => this.loadAll());
  }

  delete(transactionTypes: ITransactionTypes) {
    const modalRef = this.modalService.open(TransactionTypesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transactionTypes = transactionTypes;
  }
}
