import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductSet } from 'app/shared/model/product-set.model';

@Component({
  selector: 'jhi-product-set-detail',
  templateUrl: './product-set-detail.component.html'
})
export class ProductSetDetailComponent implements OnInit {
  productSet: IProductSet;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productSet }) => {
      this.productSet = productSet;
    });
  }

  previousState() {
    window.history.back();
  }
}
