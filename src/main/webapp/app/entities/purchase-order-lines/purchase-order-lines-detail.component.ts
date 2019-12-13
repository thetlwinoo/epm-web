import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';

@Component({
  selector: 'jhi-purchase-order-lines-detail',
  templateUrl: './purchase-order-lines-detail.component.html'
})
export class PurchaseOrderLinesDetailComponent implements OnInit {
  purchaseOrderLines: IPurchaseOrderLines;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ purchaseOrderLines }) => {
      this.purchaseOrderLines = purchaseOrderLines;
    });
  }

  previousState() {
    window.history.back();
  }
}
