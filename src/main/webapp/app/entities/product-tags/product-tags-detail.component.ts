import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductTags } from 'app/shared/model/product-tags.model';

@Component({
  selector: 'jhi-product-tags-detail',
  templateUrl: './product-tags-detail.component.html'
})
export class ProductTagsDetailComponent implements OnInit {
  productTags: IProductTags;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productTags }) => {
      this.productTags = productTags;
    });
  }

  previousState() {
    window.history.back();
  }
}
