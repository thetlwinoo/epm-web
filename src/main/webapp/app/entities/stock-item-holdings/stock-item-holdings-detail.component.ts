import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';

@Component({
  selector: 'jhi-stock-item-holdings-detail',
  templateUrl: './stock-item-holdings-detail.component.html'
})
export class StockItemHoldingsDetailComponent implements OnInit {
  stockItemHoldings: IStockItemHoldings;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockItemHoldings }) => {
      this.stockItemHoldings = stockItemHoldings;
    });
  }

  previousState() {
    window.history.back();
  }
}
