import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';

@Component({
  selector: 'jhi-upload-transactions-detail',
  templateUrl: './upload-transactions-detail.component.html'
})
export class UploadTransactionsDetailComponent implements OnInit {
  uploadTransactions: IUploadTransactions;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ uploadTransactions }) => {
      this.uploadTransactions = uploadTransactions;
    });
  }

  previousState() {
    window.history.back();
  }
}
