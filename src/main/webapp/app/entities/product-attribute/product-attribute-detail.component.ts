import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductAttribute } from 'app/shared/model/product-attribute.model';

@Component({
  selector: 'jhi-product-attribute-detail',
  templateUrl: './product-attribute-detail.component.html'
})
export class ProductAttributeDetailComponent implements OnInit {
  productAttribute: IProductAttribute;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productAttribute }) => {
      this.productAttribute = productAttribute;
    });
  }

  previousState() {
    window.history.back();
  }
}
