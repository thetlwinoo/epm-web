import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplierTransactions } from 'app/shared/model/supplier-transactions.model';

@Component({
  selector: 'jhi-supplier-transactions-detail',
  templateUrl: './supplier-transactions-detail.component.html'
})
export class SupplierTransactionsDetailComponent implements OnInit {
  supplierTransactions: ISupplierTransactions;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierTransactions }) => {
      this.supplierTransactions = supplierTransactions;
    });
  }

  previousState() {
    window.history.back();
  }
}
