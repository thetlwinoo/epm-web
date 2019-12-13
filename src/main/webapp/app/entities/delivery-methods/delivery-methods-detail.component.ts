import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';

@Component({
  selector: 'jhi-delivery-methods-detail',
  templateUrl: './delivery-methods-detail.component.html'
})
export class DeliveryMethodsDetailComponent implements OnInit {
  deliveryMethods: IDeliveryMethods;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deliveryMethods }) => {
      this.deliveryMethods = deliveryMethods;
    });
  }

  previousState() {
    window.history.back();
  }
}
