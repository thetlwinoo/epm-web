import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';

@Component({
  selector: 'jhi-supplier-imported-document-detail',
  templateUrl: './supplier-imported-document-detail.component.html'
})
export class SupplierImportedDocumentDetailComponent implements OnInit {
  supplierImportedDocument: ISupplierImportedDocument;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierImportedDocument }) => {
      this.supplierImportedDocument = supplierImportedDocument;
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
