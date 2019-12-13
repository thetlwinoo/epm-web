import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWishlists } from 'app/shared/model/wishlists.model';

@Component({
  selector: 'jhi-wishlists-detail',
  templateUrl: './wishlists-detail.component.html'
})
export class WishlistsDetailComponent implements OnInit {
  wishlists: IWishlists;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ wishlists }) => {
      this.wishlists = wishlists;
    });
  }

  previousState() {
    window.history.back();
  }
}
