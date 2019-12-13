import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductChoice } from 'app/shared/model/product-choice.model';

@Component({
  selector: 'jhi-product-choice-detail',
  templateUrl: './product-choice-detail.component.html'
})
export class ProductChoiceDetailComponent implements OnInit {
  productChoice: IProductChoice;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productChoice }) => {
      this.productChoice = productChoice;
    });
  }

  previousState() {
    window.history.back();
  }
}
