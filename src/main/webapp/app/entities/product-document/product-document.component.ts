import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductDocument } from 'app/shared/model/product-document.model';
import { ProductDocumentService } from './product-document.service';
import { ProductDocumentDeleteDialogComponent } from './product-document-delete-dialog.component';

@Component({
  selector: 'jhi-product-document',
  templateUrl: './product-document.component.html'
})
export class ProductDocumentComponent implements OnInit, OnDestroy {
  productDocuments: IProductDocument[];
  eventSubscriber: Subscription;

  constructor(
    protected productDocumentService: ProductDocumentService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.productDocumentService.query().subscribe((res: HttpResponse<IProductDocument[]>) => {
      this.productDocuments = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProductDocuments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductDocument) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInProductDocuments() {
    this.eventSubscriber = this.eventManager.subscribe('productDocumentListModification', () => this.loadAll());
  }

  delete(productDocument: IProductDocument) {
    const modalRef = this.modalService.open(ProductDocumentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productDocument = productDocument;
  }
}
