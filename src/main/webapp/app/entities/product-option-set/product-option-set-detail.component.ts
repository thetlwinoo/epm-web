import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductOptionSet } from 'app/shared/model/product-option-set.model';

@Component({
  selector: 'jhi-product-option-set-detail',
  templateUrl: './product-option-set-detail.component.html'
})
export class ProductOptionSetDetailComponent implements OnInit {
  productOptionSet: IProductOptionSet;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productOptionSet }) => {
      this.productOptionSet = productOptionSet;
    });
  }

  previousState() {
    window.history.back();
  }
}
