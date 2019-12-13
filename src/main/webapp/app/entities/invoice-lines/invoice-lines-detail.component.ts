import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvoiceLines } from 'app/shared/model/invoice-lines.model';

@Component({
  selector: 'jhi-invoice-lines-detail',
  templateUrl: './invoice-lines-detail.component.html'
})
export class InvoiceLinesDetailComponent implements OnInit {
  invoiceLines: IInvoiceLines;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ invoiceLines }) => {
      this.invoiceLines = invoiceLines;
    });
  }

  previousState() {
    window.history.back();
  }
}
