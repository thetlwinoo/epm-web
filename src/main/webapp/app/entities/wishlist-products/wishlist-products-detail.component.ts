import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWishlistProducts } from 'app/shared/model/wishlist-products.model';

@Component({
  selector: 'jhi-wishlist-products-detail',
  templateUrl: './wishlist-products-detail.component.html'
})
export class WishlistProductsDetailComponent implements OnInit {
  wishlistProducts: IWishlistProducts;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ wishlistProducts }) => {
      this.wishlistProducts = wishlistProducts;
    });
  }

  previousState() {
    window.history.back();
  }
}
