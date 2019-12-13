import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAddressTypes } from 'app/shared/model/address-types.model';

@Component({
  selector: 'jhi-address-types-detail',
  templateUrl: './address-types-detail.component.html'
})
export class AddressTypesDetailComponent implements OnInit {
  addressTypes: IAddressTypes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ addressTypes }) => {
      this.addressTypes = addressTypes;
    });
  }

  previousState() {
    window.history.back();
  }
}
