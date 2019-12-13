import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDangerousGoods } from 'app/shared/model/dangerous-goods.model';

@Component({
  selector: 'jhi-dangerous-goods-detail',
  templateUrl: './dangerous-goods-detail.component.html'
})
export class DangerousGoodsDetailComponent implements OnInit {
  dangerousGoods: IDangerousGoods;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dangerousGoods }) => {
      this.dangerousGoods = dangerousGoods;
    });
  }

  previousState() {
    window.history.back();
  }
}
