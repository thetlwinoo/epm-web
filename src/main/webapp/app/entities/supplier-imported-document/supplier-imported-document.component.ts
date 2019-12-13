import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';
import { SupplierImportedDocumentService } from './supplier-imported-document.service';
import { SupplierImportedDocumentDeleteDialogComponent } from './supplier-imported-document-delete-dialog.component';

@Component({
  selector: 'jhi-supplier-imported-document',
  templateUrl: './supplier-imported-document.component.html'
})
export class SupplierImportedDocumentComponent implements OnInit, OnDestroy {
  supplierImportedDocuments: ISupplierImportedDocument[];
  eventSubscriber: Subscription;

  constructor(
    protected supplierImportedDocumentService: SupplierImportedDocumentService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.supplierImportedDocumentService.query().subscribe((res: HttpResponse<ISupplierImportedDocument[]>) => {
      this.supplierImportedDocuments = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSupplierImportedDocuments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISupplierImportedDocument) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInSupplierImportedDocuments() {
    this.eventSubscriber = this.eventManager.subscribe('supplierImportedDocumentListModification', () => this.loadAll());
  }

  delete(supplierImportedDocument: ISupplierImportedDocument) {
    const modalRef = this.modalService.open(SupplierImportedDocumentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.supplierImportedDocument = supplierImportedDocument;
  }
}
