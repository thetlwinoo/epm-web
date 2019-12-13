import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IStockItemTemp } from 'app/shared/model/stock-item-temp.model';

@Component({
  selector: 'jhi-stock-item-temp-detail',
  templateUrl: './stock-item-temp-detail.component.html'
})
export class StockItemTempDetailComponent implements OnInit {
  stockItemTemp: IStockItemTemp;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockItemTemp }) => {
      this.stockItemTemp = stockItemTemp;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
