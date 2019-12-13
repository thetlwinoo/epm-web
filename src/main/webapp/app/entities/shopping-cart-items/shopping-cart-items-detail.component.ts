import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';

@Component({
  selector: 'jhi-shopping-cart-items-detail',
  templateUrl: './shopping-cart-items-detail.component.html'
})
export class ShoppingCartItemsDetailComponent implements OnInit {
  shoppingCartItems: IShoppingCartItems;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shoppingCartItems }) => {
      this.shoppingCartItems = shoppingCartItems;
    });
  }

  previousState() {
    window.history.back();
  }
}
