import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductOption } from 'app/shared/model/product-option.model';

@Component({
  selector: 'jhi-product-option-detail',
  templateUrl: './product-option-detail.component.html'
})
export class ProductOptionDetailComponent implements OnInit {
  productOption: IProductOption;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productOption }) => {
      this.productOption = productOption;
    });
  }

  previousState() {
    window.history.back();
  }
}
