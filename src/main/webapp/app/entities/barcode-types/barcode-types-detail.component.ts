import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBarcodeTypes } from 'app/shared/model/barcode-types.model';

@Component({
  selector: 'jhi-barcode-types-detail',
  templateUrl: './barcode-types-detail.component.html'
})
export class BarcodeTypesDetailComponent implements OnInit {
  barcodeTypes: IBarcodeTypes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ barcodeTypes }) => {
      this.barcodeTypes = barcodeTypes;
    });
  }

  previousState() {
    window.history.back();
  }
}
