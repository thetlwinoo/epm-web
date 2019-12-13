import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';

@Component({
  selector: 'jhi-purchase-orders-detail',
  templateUrl: './purchase-orders-detail.component.html'
})
export class PurchaseOrdersDetailComponent implements OnInit {
  purchaseOrders: IPurchaseOrders;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ purchaseOrders }) => {
      this.purchaseOrders = purchaseOrders;
    });
  }

  previousState() {
    window.history.back();
  }
}
