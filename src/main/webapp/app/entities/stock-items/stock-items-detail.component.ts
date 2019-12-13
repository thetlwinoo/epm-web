import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockItems } from 'app/shared/model/stock-items.model';

@Component({
  selector: 'jhi-stock-items-detail',
  templateUrl: './stock-items-detail.component.html'
})
export class StockItemsDetailComponent implements OnInit {
  stockItems: IStockItems;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockItems }) => {
      this.stockItems = stockItems;
    });
  }

  previousState() {
    window.history.back();
  }
}
