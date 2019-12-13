import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWarrantyTypes } from 'app/shared/model/warranty-types.model';

@Component({
  selector: 'jhi-warranty-types-detail',
  templateUrl: './warranty-types-detail.component.html'
})
export class WarrantyTypesDetailComponent implements OnInit {
  warrantyTypes: IWarrantyTypes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ warrantyTypes }) => {
      this.warrantyTypes = warrantyTypes;
    });
  }

  previousState() {
    window.history.back();
  }
}
