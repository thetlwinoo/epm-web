import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from './upload-transactions.service';
import { UploadTransactionsDeleteDialogComponent } from './upload-transactions-delete-dialog.component';

@Component({
  selector: 'jhi-upload-transactions',
  templateUrl: './upload-transactions.component.html'
})
export class UploadTransactionsComponent implements OnInit, OnDestroy {
  uploadTransactions: IUploadTransactions[];
  eventSubscriber: Subscription;

  constructor(
    protected uploadTransactionsService: UploadTransactionsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.uploadTransactionsService.query().subscribe((res: HttpResponse<IUploadTransactions[]>) => {
      this.uploadTransactions = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInUploadTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUploadTransactions) {
    return item.id;
  }

  registerChangeInUploadTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('uploadTransactionsListModification', () => this.loadAll());
  }

  delete(uploadTransactions: IUploadTransactions) {
    const modalRef = this.modalService.open(UploadTransactionsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.uploadTransactions = uploadTransactions;
  }
}
