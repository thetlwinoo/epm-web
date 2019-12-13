import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProductDocument } from 'app/shared/model/product-document.model';

@Component({
  selector: 'jhi-product-document-detail',
  templateUrl: './product-document-detail.component.html'
})
export class ProductDocumentDetailComponent implements OnInit {
  productDocument: IProductDocument;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productDocument }) => {
      this.productDocument = productDocument;
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
