import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompareProducts } from 'app/shared/model/compare-products.model';

@Component({
  selector: 'jhi-compare-products-detail',
  templateUrl: './compare-products-detail.component.html'
})
export class CompareProductsDetailComponent implements OnInit {
  compareProducts: ICompareProducts;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compareProducts }) => {
      this.compareProducts = compareProducts;
    });
  }

  previousState() {
    window.history.back();
  }
}
