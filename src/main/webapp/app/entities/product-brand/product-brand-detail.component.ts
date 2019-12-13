import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductBrand } from 'app/shared/model/product-brand.model';

@Component({
  selector: 'jhi-product-brand-detail',
  templateUrl: './product-brand-detail.component.html'
})
export class ProductBrandDetailComponent implements OnInit {
  productBrand: IProductBrand;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productBrand }) => {
      this.productBrand = productBrand;
    });
  }

  previousState() {
    window.history.back();
  }
}
