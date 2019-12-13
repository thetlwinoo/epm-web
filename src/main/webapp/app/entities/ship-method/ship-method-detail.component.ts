import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShipMethod } from 'app/shared/model/ship-method.model';

@Component({
  selector: 'jhi-ship-method-detail',
  templateUrl: './ship-method-detail.component.html'
})
export class ShipMethodDetailComponent implements OnInit {
  shipMethod: IShipMethod;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shipMethod }) => {
      this.shipMethod = shipMethod;
    });
  }

  previousState() {
    window.history.back();
  }
}
