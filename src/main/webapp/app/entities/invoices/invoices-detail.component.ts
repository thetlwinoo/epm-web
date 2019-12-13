import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvoices } from 'app/shared/model/invoices.model';

@Component({
  selector: 'jhi-invoices-detail',
  templateUrl: './invoices-detail.component.html'
})
export class InvoicesDetailComponent implements OnInit {
  invoices: IInvoices;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ invoices }) => {
      this.invoices = invoices;
    });
  }

  previousState() {
    window.history.back();
  }
}
