import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';

@Component({
  selector: 'jhi-shopping-carts-detail',
  templateUrl: './shopping-carts-detail.component.html'
})
export class ShoppingCartsDetailComponent implements OnInit {
  shoppingCarts: IShoppingCarts;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shoppingCarts }) => {
      this.shoppingCarts = shoppingCarts;
    });
  }

  previousState() {
    window.history.back();
  }
}
