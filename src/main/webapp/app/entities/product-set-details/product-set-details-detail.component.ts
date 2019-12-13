import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductSetDetails } from 'app/shared/model/product-set-details.model';

@Component({
  selector: 'jhi-product-set-details-detail',
  templateUrl: './product-set-details-detail.component.html'
})
export class ProductSetDetailsDetailComponent implements OnInit {
  productSetDetails: IProductSetDetails;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productSetDetails }) => {
      this.productSetDetails = productSetDetails;
    });
  }

  previousState() {
    window.history.back();
  }
}
