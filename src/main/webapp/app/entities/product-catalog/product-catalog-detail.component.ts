import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductCatalog } from 'app/shared/model/product-catalog.model';

@Component({
  selector: 'jhi-product-catalog-detail',
  templateUrl: './product-catalog-detail.component.html'
})
export class ProductCatalogDetailComponent implements OnInit {
  productCatalog: IProductCatalog;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productCatalog }) => {
      this.productCatalog = productCatalog;
    });
  }

  previousState() {
    window.history.back();
  }
}
