import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderLines } from 'app/shared/model/order-lines.model';

@Component({
  selector: 'jhi-order-lines-detail',
  templateUrl: './order-lines-detail.component.html'
})
export class OrderLinesDetailComponent implements OnInit {
  orderLines: IOrderLines;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderLines }) => {
      this.orderLines = orderLines;
    });
  }

  previousState() {
    window.history.back();
  }
}
