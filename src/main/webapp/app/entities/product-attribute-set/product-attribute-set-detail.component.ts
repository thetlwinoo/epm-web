import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';

@Component({
  selector: 'jhi-product-attribute-set-detail',
  templateUrl: './product-attribute-set-detail.component.html'
})
export class ProductAttributeSetDetailComponent implements OnInit {
  productAttributeSet: IProductAttributeSet;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productAttributeSet }) => {
      this.productAttributeSet = productAttributeSet;
    });
  }

  previousState() {
    window.history.back();
  }
}
